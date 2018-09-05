package org.leo.syntax

/**
  * Traits are used to share interfaces and fields between classes
  * objects and classes can extend them but they cannot be instantiated
  */
trait ReferenceTrait {

  // traits can have abstract methods
  def doThings(s: String): Unit

  // traits can have default method implementation
  def defaultMethod(s: Int) = {
    s + 1
  }
}

abstract class AbstractExtended {
  def doAbstractly(): Unit
}

// when extending a Trait, the class must implement the undefined methods or be declared abstract
// here ReferenceTrait is said to be a mixed in or a mixin
// this is a fancy word to say that a given trait is here not to be the parent of the extending class
// but rather to add (mix in) some implemented functions of the trait, what helps achieving this is the default
// implementations we have in a trait (same for java 8 interface default methods)
// to mix in a trait, we use the with keyword which only works on traits
class CustomExtender extends AbstractExtended with ReferenceTrait {

  // overriding trait method
  override def doThings(s: String) = println(s)

  // overriding abstract method
  override def doAbstractly(): Unit = println("Abstract no more")
}
