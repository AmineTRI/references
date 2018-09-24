package org.leo.syntax

/**
  * a trait or an abstract class can have an abstract type in scala
  * In most cases, it is possible to turn abstract type members into generic type parameters and vice versa
  * but there are cases when this is not possible
  */
sealed trait AbstractType {
  type T
  val element: T
}

abstract class SeqAbstractType extends AbstractType {
  // we can have multiple abstract types
  type U

  // we can set lower or upper bound to abstract types the same way we do with type parameters
  type T <: Seq[U]

  def length = element.length
}

abstract class IntSeqAbstractType extends SeqAbstractType {
  // this is how we set the type of an abstract type
  type U = Int
}

// companion for tests
object AbstractType {

  // abstract types are commonly used with anonymous implementations
  def newIntSeqAbstractType(elem1: Int, elem2: Int): IntSeqAbstractType =
    new IntSeqAbstractType {
      // fixing the remaining abstract type T respecting the upper bound to Seq[U] where U is already fixed to Int
      type T = List[U]
      val element = List(elem1, elem2)
    }

  val buf = newIntSeqAbstractType(7, 8)
  println("length = " + buf.length)
  println("content = " + buf.element)
}
