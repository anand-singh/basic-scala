package com.techmonad.basicscala.domain

import java.util.UUID
import io.scalaland.chimney.dsl._

object Request {

  case class Create(name: String, email: String)

  case class Find(id: UUID) extends AnyVal

  def toUser: Create => User = _.into[User].withFieldComputed(_.id, _ => UUID.randomUUID()).transform

}
