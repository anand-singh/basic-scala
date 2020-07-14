package com.techmonad.basicscala.service

import java.util.UUID

import com.techmonad.basicscala.domain.Request.{HttpRequest, HttpResponse, NewUser}
import com.techmonad.basicscala.domain.User
import com.techmonad.basicscala.repo.UserRepo
import com.techmonad.basicscala.util.Mappable
import com.techmonad.basicscala.util.MappableConversion._

import scala.concurrent.Future

abstract class UserService[F[_]: Mappable] {
  def findAllUsers: HttpRequest[Unit] => F[HttpResponse[List[User]]]
  def findUserById: HttpRequest[UUID] => F[HttpResponse[Option[User]]]
  def registerUser: HttpRequest[NewUser] => F[HttpResponse[User]]
}

class UserServiceInterpreter(repo: UserRepo[Future]) extends UserService {
  override def findAllUsers: HttpRequest[Unit] => Future[HttpResponse[List[User]]] = ???

  override def findUserById: HttpRequest[UUID] => Future[HttpResponse[Option[User]]] = ???

  override def registerUser: HttpRequest[NewUser] => Future[HttpResponse[User]] = ???
}
