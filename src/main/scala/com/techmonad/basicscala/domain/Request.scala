package com.techmonad.basicscala.domain

import java.util.UUID
import io.scalaland.chimney.dsl._

object Request {

  case class HttpRequest[A](requestId: UUID, body: A)

  case class HttpResponse[R](responseId: UUID, body: R)

  case class NewUser(name: String, email: String)

  case class FindUser(id: UUID) extends AnyVal

  def toUser: NewUser => User = _.into[User].withFieldComputed(_.id, _ => UUID.randomUUID()).transform

}
