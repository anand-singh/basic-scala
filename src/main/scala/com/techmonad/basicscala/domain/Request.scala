package com.techmonad.basicscala.domain

import java.util.UUID

object Request {

  case class HttpRequest[A](requestId: UUID, body: A)

  case class HttpResponse[R](responseId: UUID, body: R)

  case class NewUser(name: String, email: String)

}
