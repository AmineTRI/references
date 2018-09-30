package org.leo.examples

import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

/**
  * ScalaTest provides a DSL to express assertions using the word should
  * all you need is to mixin the Matchers Trait
  */
class MatchersTest extends FunSuite with Matchers with BeforeAndAfter {

  var result: Int = _
  var list: List[Int] = _
  var string: String = _

  before {
    result = 3
    list = List(1, 2, 3, 4)
    string = "hi"
  }

  result should equal (3) // can customize equality
  result should === (3)   // can customize equality and enforce type constraints
  result should be (3)    // cannot customize equality, so fastest to compile
  result shouldEqual 3    // can customize equality, no parentheses required
  result shouldBe 3       // cannot customize equality, so fastest to compile, no parentheses required

  // for any object that have a size and an length we can do
  list should have size 4
  string should have length 2

  /**
    * Of course there is more functions with Matchers, we won't be trying them all here
    * because it will take like forever and i don't want to, but you got the idea
    */
}
