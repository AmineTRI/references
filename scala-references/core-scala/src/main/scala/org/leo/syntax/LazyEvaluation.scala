package org.leo.syntax

/**
  * Testing laziness in scala
  * Lazy evaluation is the contrast of strict evaluation or eager evaluation
  * It is an evaluation strategy that delays expression evaluation until the later is needed
  * it may help in circular dependencies and can improve calculation efficiency by not making computations until needed
  * or even never if not needed
  */
object LazyEvaluation extends App {

  /**
    * This is how we declare a lazy value
    * only val(s) can can be lazy, var(s) cannot
    * a lazy val is only evaluated the first time it is accessed and never again
    */
  lazy val lazyValue = 3

  /**
    * In addition to strict collections (List, Map, Set, Vector ...), scala have non strict collection
    * that are lazy by default, these are called Streams
    */
  val list = (1 to 10).toList         // this list is eagerly  evaluated upon its declaration, and memory is
                                      // immediately allocated for all its elements

  val stream = (1 to 10).toStream     // the stream head(1) is eagerly evaluated, but its tail (the rest of it) is not
                                      // yet evaluated

  /**
    * Streams are like collections but are lazy evaluated, that's why we can have infinite elements in them
    */
  val conStream = 1 #:: 2 #:: 3 #:: Stream.empty        // like collections cons :: we have stream cons #::

  /**
    * Another aspect of scala laziness are the by name parameters
    * parameters are evaluated upon function calls, even before the function body
    * so we can just ad => before the param type to make it a by name parameter that is not evaluated until it is used
    * inside the method
    */
  def byNameCall(a: => Int) = List(a, a, a)

  // the problem with by name parameters that they are (unlike lazy val s) evaluated each time they are called
  // this prints the message three times and results in list with different values
  print(byNameCall({println("computing a"); util.Random.nextInt(10)}))

  // to implement real laziness in method we can do this
  def realLazyCallByName(a: => Int) = {
    lazy val b = a
    List(b, b, b)
  }

  // this prints the message only once and results in list with the same value repeated
  print(realLazyCallByName({println("computing a"); util.Random.nextInt(10)}))
}
