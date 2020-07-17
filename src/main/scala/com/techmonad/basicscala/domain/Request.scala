package com.techmonad.basicscala.domain

import java.util.UUID

import scala.concurrent.Future

object Request {

  case class HttpRequest[A](requestId: UUID, body: A)

  case class HttpResponse[R](responseId: UUID, body: R)

  case class NewUser(name: String, email: String)

  type WebRequest[A, B] = HttpRequest[A] => Future[HttpResponse[B]]
  type TransformReq[A]  = HttpRequest[A] => A
  type TransformRes[A]  = Future[A] => Future[HttpResponse[A]]

}
