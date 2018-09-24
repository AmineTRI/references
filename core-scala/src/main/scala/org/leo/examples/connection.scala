package org.leo.examples


object connection {

  def getCurrentValue(currency: String) = 0.5D

  @throws(classOf[Exception])
  def buy(amount: Int, rate: Double): Int = if (amount > 0) amount else throw new Exception

}
