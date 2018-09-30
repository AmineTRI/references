package org.leo.examples

import org.scalatest.{BeforeAndAfter, FunSuite}

/**
  * Say hello to FunSuite here, the Fun stands for function actually
  * We use these tests when we want to do as if we are using JUnit for example, for TDD commonly
  * it provides the test() syntax to test your code
  * We can use the BeforeAndAfter trait to factorize code you want to execute before and after each test
  */
class PizzaUnitTest extends FunSuite with BeforeAndAfter {

  var pizza : Pizza = _

  /**
    * This section will be executed before each test
    */
  before {
    pizza = new Pizza
  }

  test("new pizza has zero toppings") {
    assert(pizza.getToppings.size == 0)
  }

  test("adding one topping") {
    pizza.addTopping(Topping("green olives"))
    assert(pizza.getToppings.size === 1)
  }

  test("burning the pizz") {
    // aside from asserts, we can test exception throwing also
    val thrown = intercept[Exception] {
      pizza.burnPizza
    }

    assert(thrown.getMessage === "Pizza is Burning!")
  }

  // you can use pending to say that you would want to put a test here in the future and you just don't want to
  // write it now (and why the fuck would you ??, i mean writing tests is boring and shit right ?)
  test ("test pizza pricing") (pending)

  /**
    * This section will be executed after each test
    */
  after {
    println("Test is over")
  }
}
