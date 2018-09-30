package org.leo.examples

import org.scalatest.Matchers
import org.scalatest.prop.{PropertyChecks, TableDrivenPropertyChecks}

class PropertyBasedTest extends PropertyChecks with Matchers {

  /**
    * Generator driven property check
    */
  forAll { (n: Int, d: Int) =>

    whenever (d != 0 && d != Integer.MIN_VALUE
      && n != Integer.MIN_VALUE) {

      val f = new Fraction(n, d)

      if (n < 0 && d < 0 || n > 0 && d > 0)
        f.numer should be > 0
      else if (n != 0)
        f.numer should be < 0
      else
        f.numer should be === 0

      f.denom should be > 0
    }
  }
}
