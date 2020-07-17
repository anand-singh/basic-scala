package com.techmonad.basicscala.util

import com.typesafe.scalalogging.Logger

trait ScalaLogger {

  val logger: Logger = Logger(this.getClass)

  val info: String => Unit  = v => logger.info(v)
  val debug: String => Unit = v => logger.debug(v)
  val error: String => Unit = v => logger.error(v)
  val warn: String => Unit  = v => logger.warn(v)

}
