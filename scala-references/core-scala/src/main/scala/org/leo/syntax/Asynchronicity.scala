package org.leo.syntax

import java.lang.Exception

import scala.collection.mutable
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.util.{Failure, Success}

/**
  * In this object we will be discussing two aspects of asynchronicity
  * which are Futures and Promises
  */
object Asynchronicity extends App {

  /**
    * Before we dig in Futures and Promises, a word a bout the concept of non blocking operations
    * A non blocking operation is usually an operation that runs in parallel to the flow of the
    * program, its failure does not necessarily imply the failure of the program and the flow may
    * continue (does not block) while the non blocking operation is not yet completed
    */

  /**
    * Another concept that is fundamental when dealing with Futures and Promises is ExecutionContext
    * It is responsible for executing the underlying computation of the Futures and Promises
    * It is similar to a java Executor and can execute computation in a new thread, a pooled thread
    * or in the current thread
    * We usually use the scala.concurrent provided ExecutionContext.global called the Global Execution
    * Context which is backed by a ForkJoinPool
    * The ForkJoinPool can manage a limited number of threads (the max called parallelism level refers
    * to the number of available processors)
    * ForkJoinPool can handle more concurrently blocking computations than the parallelism level if each
    * call is wrapped in a blocking call
    */

  // this is equivalent to implicit val ec = ExecutionContext.global
  import org.leo.examples.connection

  import ExecutionContext.Implicits.global
  import scala.concurrent.blocking

  /**
    * All the talk about the ForkJoinPool limits is to say that it should not be used for long
    * lasting blocking operation even with blocking calls
    * for example the following code will use 3200 threads
    */
  for (i <- 1 to 32000) {
    Future {
      blocking {
        Thread.sleep(999999)
      } //(global)               // global is implicitly passed as and ExecutionContext
    }
  }

  /**
    * We can also use Java's Executor as ExecutionContext like this :
    * ExecutionContext.fromExecutor(new ThreadPoolExecutor( /* your configuration */ ))
    */

  /**
    * We also may want to use the current thread using ExecutionContext.fromExecutor
    * but this is discouraged because it may introduce non determinism in the execution of the
    * You really don't need to know more about this for now
    */

  /**
    * Futures : is a placeholder object for a value which may be available at some point
    * this value is usually  the result of some operations
    * A future is either completed(if its operations are completed) or not completed otherwise
    * If the operations complete with success, the future is then completed with a value and it is
    * said to be successfully completed
    * Otherwise, if the operations throws an exception, the future is completed with an exception
    * and it is said to be failed with that exception
    * The most important property of a future is the following : it can only be assigned once
    * meaning once the future object is given a value or and exception it becomes immutable
    */

  /**
    * We create a future like this
    */

  // this will be a String value at some point
  val someFuture: Future[String] = Future { // we clearly calling an apply here
    // some operations
    "value"
  } //(global)   // we pass the execution context here but since we have one as implicit value
  // so we omit passing it here

  // example for futures use : the friends list can be retrieved with a call to network server for
  // example, we are not compelled to wait for it to continue other computations
  // so we put that action into a future and as soon as the friends fetch is done
  // the list of friends will be available in the f future value
  import org.leo.examples.socialNetwork
  import org.leo.examples.socialNetwork._

  val session = socialNetwork.createSessionFor("user", "pwd")
  val friends: Future[List[Friend]] = Future {
    session.getFriends()
  }

  /**
    * That was nice, but we are more interested in getting the future value once available, we could block
    * the future (from inside it or outside, we will see that later) and wait for its computation
    * but in scala we can do it in a non blocking way using callbacks
    * all we need to do is to register a callback on the future that will be executed asynchronously
    * once the future is completed
    * asynchronously means in another thread (or maybe the same which may happen if the callback is registered when the
    * future is already completed)
    */

  /**
    * Before we talk a bout callbacks,a word about Try[T] : it's like Option[T] that either hods None or Some[T], or Either[T,S]
    * It's a monad that holds either Success[T] or Failure[T] in case of an exception
    * you can think of it as some kind of Either[Throwable, T] specialized when the left type is a throwable
    */

  /**
    * the onComplete method registers a callback that takes a function of type Try[T] => U that will be applied
    * on Success[T] if the future complete successfully or Failure[T] otherwise
    */
  val posts: Future[List[Post]] = Future {
    session.getPosts()
  }

  posts onComplete {
    case Success(posts) => for (post <- posts) print(post.text)
    case Failure(t) => print("Error: " + t.getMessage)
  }

  /**
    * We can register a callback to call only when the future completes with a value
    * this method is foreach
    */
  posts foreach {
    posts => for (post <- posts) print(post.text)
  }

  /**
    * Generalities about foreach and onComplete : they both return Unit so we cannot chain them up
    * its because we never know the order of execution of a callback (depends on what thread executing it)
    * but while the order is not guaranteed we only have the callback will be executed by some thread
    * at some time after the future is completed, we say it is executed eventually
    */

  /**
    * The problem with foreach and onComplete is that it will result in bulky code if we need to chain
    * up futures : look at this example
    */

  import org.leo.examples.connection

  def isProfitable(rate: Double) = rate > 1

  def isProfitable(r: Double, q: Double) = r > q

  // first future : we get the actual rate
  val rate = Future[Double] {
    connection.getCurrentValue("USD")
  }

  rate foreach { r =>
    // second future inside the callback of the first one
    val purchase = Future {
      // if the rate is profitable we buy
      if (isProfitable(r)) connection.buy(2, r)
      else throw new Exception("not profitable")
    }

    // then we register a callback on the second future
    purchase foreach { _ =>
      print("purchased 2 USD")
    }
  }

  /**
    * 1/ that code is bulky
    * 2/ the purchase future is not in the scope of the general code and is nested in the
    * foreach callback so we cannot use it outside
    */

  /**
    * To resolve such issues (nested futures and inability to chain them) we use what we call
    * combinators to have a more direct composition
    * Let's re use the rate future with the map combinator
    */

  /**
    * map doesn't actually create a new Future, all it does is : given a future (rate) and a mapping function
    * produce a future that is completed with the mapped value of the original future once the
    * later is completed
    * It kind of creates a new Future that's completion is directly related to the completion of the original one
    * If the first future is failed or the isProfitable is false then the purchase future (the mapped one) will complete
    * with failure on the appropriate exceptionc
    *
    * Here we have eliminated one foreach and the nesting
    */
  val purchase = rate map { r =>
    if (isProfitable(r)) connection.buy(2, r)
    else throw new Exception("not profitable")
  }

  /**
    * Futures also have flatMap and withFilter combinators allowing them to be used in
    * for comprehensions
    */
  val usdQuote = Future {
    connection.getCurrentValue("USD")
  }
  val chfQuote = Future {
    connection.getCurrentValue("CHF")
  }

  val advancedPurchase = for {
    usd <- usdQuote
    chf <- chfQuote
    if isProfitable(usd, chf)
  } yield connection.buy(3, chf)

  advancedPurchase foreach { _ =>
    println("Purchased " + 3 + " CHF")
  }

  /**
    * the same can be written as : (which is pretty much as fucked up as the previous)
    * but we don't care for it is not so common to use flatMap on futures
    *
    * The filter or withFilter creates a new future with the value of the original future
    * if the predicate is satisfied otherwise the new future is failed with NoSuchElementException
    */
  val purchaseFlatMapped = usdQuote flatMap {
    usd =>
      chfQuote
        .withFilter(chf => isProfitable(chf, usd)) // withFilter can be replaced by filter
        .map(chf => connection.buy(3, chf))
  }

  /**
    * Since a future can complete with a failure, if we do not want the future to complete with an exception
    * we can handle it with recover
    */
  val purchaseRecovered: Future[Int] = usdQuote map {
    quote => connection.buy(0, quote)
  } recover {
    case e : Exception => 0
  }

  /**
    * The fallback creates a new future that completes with the first future is it completes with success
    * otherwise it completes with the second's, if even the second fail it completes with the first exception
    */
  val someQuote = usdQuote fallbackTo chfQuote

  /**
    * The andThen combinator does some side effects then returns a new completed future with the same
    * result as the first one, allowing multiple andThen to be chained up with ordered calls
    */
  val allposts = mutable.Set[Post]()

  Future {
    session.getPosts()
  } andThen {
    case Success(posts) => allposts ++= posts
  } andThen {
    case _ =>
      for (post <- allposts) print(post.text)
  }

  /**
    * All combinators are purely functional and every combinator results in a new future
    */

  /**
    * Futures have what we call failed projection, if the original future fails then its failed projection
    * returns a future with a value of type Throwable if else (original future succeeds) then its projections
    * fails with a NoSuchElementException
    */
  val f = Future {
    2 / 0
  }

  f.failed.foreach(exc => println(exc))

  // which could be written as a for comprehension
  for (exc <- f.failed) println(exc)

  /**
    * Blocking futures: future are generally asynchronous and do not block the underlying
    * execution threads, but we can block them by two ways
    */

  // block from inside : we notify the execution context of a blocking call
  val blockable = Future {
    blocking {
      // ... some code with a lock
    }
  }

  // from outside the future
  Await.result(blockable, Duration.Zero)

  /**
    * Promises : unlike futures which are read-only placeholder object for a future value,
    * a promise is a writable, single-assignment container that completes a future (waw !!!)
    * a promise completes its future with  the success or the failure method
    *
    * It is as if the promise fulfilment is the operation that completes its future
    * the best example is the producer consumer example
    */
  val p = Promise[Int]()
  val ft = p.future

  val producer = Future {
    val r = 3
    p success r     // ft is completed
    //continueDoingSomethingUnrelated()
  }

  val consumer = Future {
    //startDoingSomething()
    ft foreach { r =>         // trigered when ft is completed and may began before continueDoingSomethingUnrelated
      //doSomethingWithResult()
    }
  }

  // this is how we fail a promise
  p failure(new IllegalStateException)

  // a promise can be completed using the complete method that takes a try[T] : either a Failure[Throwable]
  // of a Success[T]

  // calling success, fail or complete on an already completed promise throw and exception

  /**
    * A promise can be completed with another future, after the later is completed, the promise will get completed
    * by the value of completion of the passed future
    */
  val aFuture = Future { 1 }
  val aPromise = Promise[Int]()

  aPromise completeWith aFuture

  aPromise.future foreach { x =>
    println(x)
  }
}
