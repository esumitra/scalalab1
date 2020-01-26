package com.example

import org.scalatest.{ WordSpec, BeforeAndAfterAll, Matchers }

class Problem1Test
  extends WordSpec
  with Matchers
  with BeforeAndAfterAll {

  val eps = 0.01 // specify error range for loss of precision with Double

  "celsiusToFahrenheit" should {
    "convert correctly for 0" in {
      Problem1.celsiusToFahrenheit(0.0) shouldBe 32.0
    }
    "convert correctly for 32" in {
      Problem1.celsiusToFahrenheit(-17.78) shouldBe 0.0 +- eps // specify error range for loss of precision with Double
    }
    "convert correctly for 100" in {
      Problem1.celsiusToFahrenheit(100) shouldBe 212.0
    }
  }

  "stringToTemperature" should {
    "convert a valid double string" in {
      Problem1.stringToTemperature("12.2") shouldBe Some(12.2)
    }
    "skip a comment line" in {
      Problem1.stringToTemperature("# should be skipped") shouldBe None
    }
    "skip a invalid string" in {
      Problem1.stringToTemperature("12.2X") shouldBe None
    }
  }

}
