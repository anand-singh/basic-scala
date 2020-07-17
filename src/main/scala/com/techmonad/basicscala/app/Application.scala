package com.techmonad.basicscala.app

import java.util.UUID

import com.techmonad.basicscala.domain.Request.{HttpRequest, NewUser}
import com.techmonad.basicscala.service.UserService

import scala.concurrent.ExecutionContext.Implicits.global

object Application extends App {

  val service = UserService()

  val uuid = UUID.randomUUID()

  val regRequest      = HttpRequest[NewUser](uuid, NewUser("X", "x@x.com"))
  val findByIdRequest = HttpRequest[UUID](uuid, uuid)
  val findAllRequest  = HttpRequest[Unit](uuid, ())

  for {
    _ <- service.registerUser(regRequest)
    _ <- service.findUserById(findByIdRequest)
    _ <- service.findAllUsers(findAllRequest)
  } yield ()

}

trait Greeting {
  lazy val greeting: String = "hello"
}
