package org.leo.examples

import org.scalamock.proxy.ProxyMockFactory
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

/**
  * ScalaMock is native mocking framework to mock objects and functions
  */
class MockTest extends FlatSpec with MockFactory {

  /**
    * Function Mock
    */
  val functionMock = mockFunction[Int, String]               // m takes an Int and return a String
  functionMock expects 42 returning "Forty Two" once()       // m expects to be called once with 42 and return Forty Two
                                                             // the once can be omitted since expects are by default once

  functionMock stubs 1 returning "one"                       // stubs is the same as expects ... anyNumberOfTimes

  /**
    * Object Mock
    */
  val objectMock = mock[Mockable]
  (objectMock.forward _).expects(3).returning("ok")

  /**
    * And so on and so one, this is boring me seriously, we need a real case here to show the tru power od mocks
    */

}

