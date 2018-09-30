package org.leo.examples

import org.scalatest.AsyncFlatSpec

import scala.concurrent.Future

/**
  * ScalaTest support asynchronous non blocking testing for futures, since you need not to block until the future
  * completes to make assertions on its value, you can instead map the assertions to the future and produce a future of
  * assertions Future[Assertion] that will complete asynchronously
  *
  * The ScalaTest Suites and Specs traits have their asynchronous counter part like AsyncFunSuite AsyncFunSpec etc ...
  */
class AsynchronousTest extends AsyncFlatSpec {

  /**
    * This is an occasion to use the asterisk syntax in the parameter list
    * Int* means any number of Int, actually its a sequence of Int but instead of calling
    * addSoon(Seq(1,2,3)) we call addSoon(1,2,3)
    */
  def addSoon(addends: Int*): Future[Int] = Future { addends.sum }

  behavior of "addSoon"

  it should "eventually compute a sum of passed Ints" in {
    val futureSum: Future[Int] = addSoon(1, 2)
    // You can map assertions onto a Future, then return
    // the resulting Future[Assertion] to ScalaTest:
    futureSum map { sum => assert(sum == 3) }
  }

  def addNow(addends: Int*): Int = addends.sum

  "addNow" should "immediately compute a sum of passed Ints" in {
    val sum: Int = addNow(1, 2)
    // You can also write synchronous tests. The body
    // must have result type Assertion:
    assert(sum == 3)
  }

  /**
    * Of course there are many other function related to testing futures but we will not test them all for now
    */
}
