package org.leo.examples

import org.scalatest.tagobjects.Slow
import org.scalatest.{FlatSpec, Tag}

import scala.collection.mutable.Stack

/**
  * ScalaTest allows us to tag tests in order to filter them when running tests
  */
class TagTest extends FlatSpec {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  // ScalaTest provides only one default tag : ignore , it allows you to switch off the tagged test
  ignore should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[String]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
  }

  /**
    * Passing tags depends on the test type used, for FlatSpec it is used like this
    */
  "The Scala language" must "add correctly" taggedAs (Slow) in {
    val sum = 1 + 1
    assert(sum === 2)
  }

  it must "subtract correctly" taggedAs(Slow, CustomTag) in {
    val diff = 4 - 1
    assert(diff === 3)
  }

  /**
    * For other types we can pass the tag object to the test method or the it method for example just after the
    * test name for example test("first test", Slow, CustomTag) or it("should work", CustomTag)
    */
}

object CustomTag extends Tag("myTag")
