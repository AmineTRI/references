package org.leo.examples

import org.scalatest.FunSuite

/**
  * Lets test all assertion types
  * Assertions are defined in the Assertion Trait extended by the Suite super trait of all style traits
  */
class AssertionTest extends FunSuite {

  // simple assert always takes a boolean, it automatically generate an understandable error message if false
  assert(2 > 1)

  // we can insert a custom error message, it is called a clue and can be used with most assertions
  assert(2 > 1, "clue : that was expected")

  // a more stylish assert is when we distinguish the expected result
  assertResult(3, "clue: custom error message") {
    1 + 2
  }

  // we can also make a test fail, commented (we don't want these tests to fail)
  // fail
  //fail("i've got a bad feeling about this test")

  // or force success (useful in async type tests)
  //succeed         // commented to avoid warnings

  // testing if some Exception is thrown
  val s = "hi"
  withClue("clue: custom error message") {
    assertThrows[IndexOutOfBoundsException] { // Result type: Assertion
      s.charAt(-1)
    }
  }

  // intercepting a thrown Exception
  val caught = intercept[IndexOutOfBoundsException] { // Result type: IndexOutOfBoundsException
      s.charAt(-1)
  }
  assert(caught.getMessage.indexOf("-1") != -1)

  // checking if some code does not compiles
  assertDoesNotCompile("val a: String = 1")

  // checking if error is a type error
  assertTypeError("val a: String = 1")

  //checking if some code compiles
  assertCompiles("val b: Int = 1")

  // Assumptions are usually used at the beginning of test
  // they are used to cancel tests if conditions required to do test are not satisfied
  val databaseConnection = true
  val internetConnection = false
  assume(databaseConnection, "test canceled because database is down")
  //assume(internetConnection, "test canceled because internet connection is down")   // commented to avoid canceling

  // we can also explicitly cancel the test
  //cancel
  //cancel("test canceled for no reason")

  // to give name to a test we use
  test("my test") {
    assert(1 > 0)
  }
}
