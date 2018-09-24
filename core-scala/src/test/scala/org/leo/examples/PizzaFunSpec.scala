package org.leo.examples

import org.scalatest.{BeforeAndAfter, FunSpec}

/**
  * Another way to write BDD style specification in a nested structure
  */
class PizzaFunSpec extends FunSpec with BeforeAndAfter {

  var pizza: Pizza = _

  before {
    pizza = new Pizza
  }

  describe("A Pizza") {
    describe("when backed") {
      it("should have no toppings") {
        assertResult(0) {
          pizza.getToppings.length
        }
      }

      it("should throw an Exception if burned") {
        assertThrows[Exception] {
          pizza.burnPizza
        }
      }
    }
  }
}
