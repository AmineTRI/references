package org.leo.examples

import org.scalatest.{BeforeAndAfter, FlatSpec}

/**
  * FlatSpec is to use for BDD style tests as a first step for people moving from JUnit like tests to
  * BDD
  */
class PizzaFlatSpec extends FlatSpec with BeforeAndAfter {

  var pizza: Pizza = _

  before {
    pizza = new Pizza
  }

  // the syntaxe is "sentence" verb from must, should or can "sentence" in {tests}
  "A pizza" should "should start with no toppings" in {
    assert(pizza.getToppings.size == 0)
  }

  // we can make more test on the same object with it
  it can "add more toppings" in {
    pizza.addTopping(Topping("olive"))
    assert(pizza.getToppings.size == 1)
  }

  it must "throw Exception if burnt" in {
    assertThrows[Exception] {
      pizza.burnPizza
    }
  }
}
