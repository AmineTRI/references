package org.leo.examples

import scala.collection.mutable.ArrayBuffer

/**
  * A class to test Scala Unit features upon
  * I do love Pizza thought
  */
class Pizza {
  private val toppings = new ArrayBuffer[Topping]

  def addTopping(t: Topping) = toppings += t
  def removeTopping(t: Topping) = toppings -= t
  def getToppings = toppings.toList

  def burnPizza = throw new Exception("Pizza is Burning!")
}

case class Topping(name: String)
