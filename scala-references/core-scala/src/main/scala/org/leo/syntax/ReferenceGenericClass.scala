package org.leo.syntax

import org.leo.data._

/**
  * Generic classes in scala works quite the same as in java
  * A, B are the common given name to the type parameters
  * like java, generics in scala are invariant meaning that Gen[A] is a subtype of Gen[B]
  * only if A = B
  * Variance is the relationship between types subtyping and their generic types subtyping
  * Covariance is done using +A this mean Gen[+A] if A is a subtype of B then Gen[A] is a subtype of Gen[B]
  * Contravariance is the opposite of what covariance does meaning for some class Gen[-A] if A is a subtype of B,
  * then Gen[B] is a subtype of Gen[A]
  * `A `<`: `B is used to define upper type bound this means the generic class accepts only types A that are subtypes of B
  * `A `>`: `B is used to define lower type bound this means the generic class accepts only types A that are supertypes of B
  */
class ReferenceGenericClass[-A <: AbstractDataType1, +B >: DataType2AA] {

}
