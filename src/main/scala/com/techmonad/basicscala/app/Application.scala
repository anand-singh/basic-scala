package com.techmonad.basicscala.app

import scala.util.chaining._

object Application extends Greeting with App {
  greeting
}

trait Greeting {
  lazy val greeting: String = "hello".tap(println)
}
