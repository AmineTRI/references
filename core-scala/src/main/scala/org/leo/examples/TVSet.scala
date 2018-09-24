package org.leo.examples

class TVSet {
  private var on: Boolean = false

  def isOn: Boolean = on

  def pressPowerButton() = on = !on
}
