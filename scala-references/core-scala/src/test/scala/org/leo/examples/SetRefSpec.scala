package org.leo.examples

import org.scalatest.refspec.RefSpec

/**
  * Alright i am now really pissed up by all these syntax for tests
  * this is madness, please stop these things
  * at least 7 traits for 7 different styles of Spec tests ?? why the fuck would you do that ???
  */
class SetRefSpec extends RefSpec {

  object `A Set` {
    object `when empty` {
      def `should have size 0` {
        assert(Set.empty.size == 0)
      }

      def `should produce NoSuchElementException when head is invoked` {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}