package com.techmonad.basicscala

import com.techmonad.basicscala.app.Application
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HelloSpec extends AnyFlatSpec with Matchers {
  "The Hello object" should "say hello" in {
    Application.greeting shouldEqual "hello"
  }
}
