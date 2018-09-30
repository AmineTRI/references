package org.leo.syntax

/**
  * Compound types are used when we need an object to be expressed as being a subtype of more than one type
  * when calling it as a method argument or even when instantiating it
  */
class CompoundType extends FirstType with SecondType

class NotYetComposedType

trait FirstType {
  def doFirstTypeStuff = print("First Type")
}

trait SecondType {
  def doSecondTypeStuff = print("Second Type")
}

/**
  * Companion object for tests
  */
object CompoundType {

  // here is the use case of a compound type
  def compoundTest(obj: FirstType with SecondType) = {
    obj.doFirstTypeStuff;
    obj.doSecondTypeStuff
  }

  val compoundType = new CompoundType
  compoundTest(compoundType);

  // we can create a compound type object even if it does not extend the wanted traits already
  val notYetComposed = new NotYetComposedType with FirstType with SecondType
  compoundTest(notYetComposed)
}
