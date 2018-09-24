package org.leo.examples

class Fraction(n: Int, d: Int) {

  // wtf are those ?? pretty awesome !!!
  require(d != 0)
  require(d != Integer.MIN_VALUE)
  require(n != Integer.MIN_VALUE)

  val numer = if (d < 0) -1 * n else n
  val denom = d.abs

  override def toString = numer + " / " + denom
}
