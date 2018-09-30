package org.leo.syntax

/**
  * Self types is the process by which you make a trait using another one us a dependency,
  * if a trait B is a self type of Trait A then we cannot mix in A without mixing B
  * Also we can refer to B's members withing A (extending "this" most commonly or using an identifier
  * like self as an alias to the extended this)
  * It is as if A and B were one single traits that were split in two traits
  *
  * Self types are used in Dependency Injection and also in the Cake Pattern
  *
  * if we did not mix in B this code will not compile since it is required by A
  */
class SelfType extends A with B with C

trait A {
  /**
    * Any identifier can be used in the self type annotation (self, this, foo ...)
    * using any identifier will still be an alias to this
    * The advantage of using an identifier other than this, is that this, will not be shadowed if an inner class
    * is defined inside the class where we are using the self type annotation
    * We can have more thant one trait as self type using with like in this example
    */
  this: B with C =>
}

trait B
trait C

// you can see the Cake Pattern class to see how we can use the self type annotation to implement dependency injection
