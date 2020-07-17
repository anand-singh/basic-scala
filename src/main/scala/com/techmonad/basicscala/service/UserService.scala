package com.techmonad.basicscala.service

import java.util.UUID

import com.techmonad.basicscala.domain.Request.{HttpRequest, HttpResponse, NewUser, TransformReq, TransformRes, WebRequest}
import com.techmonad.basicscala.domain.User
import com.techmonad.basicscala.repo.UserRepo
import com.techmonad.basicscala.util.MappableConversion._
import com.techmonad.basicscala.util.{Mappable, ScalaLogger}
import io.scalaland.chimney.dsl._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.chaining._

abstract class UserService[F[_]: Mappable] {
  def findAllUsers: HttpRequest[Unit] => F[HttpResponse[List[User]]]
  def findUserById: HttpRequest[UUID] => F[HttpResponse[Option[User]]]
  def registerUser: HttpRequest[NewUser] => F[HttpResponse[User]]
}

class UserServiceInterpreter(repo: UserRepo[Future]) extends UserService with ScalaLogger {

  override def findAllUsers: WebRequest[Unit, List[User]] = transformReq[Unit] andThen repo.all andThen transformRes[List[User]]

  override def findUserById: WebRequest[UUID, Option[User]] = transformReq[UUID] andThen repo.find andThen transformRes[Option[User]]

  override def registerUser: WebRequest[NewUser, User] = transformReq[NewUser] andThen toUser andThen repo.insert andThen transformRes[User]

  //Register User
  val toUser: NewUser => User = _.into[User].withFieldComputed(_.id, _ => UUID.randomUUID()).transform

  //Transform Req/Res
  def transformReq[A]: TransformReq[A] = _.tap(r => info(s"Request received ID - [${r.requestId}]")).body
  def transformRes[A]: TransformRes[A] = _.map(b => HttpResponse[A](UUID.randomUUID(), b).tap(r => info(s"Response - [$r]")))

}

object UserService {
  def apply(): UserService[Future] = new UserServiceInterpreter(UserRepo())
}
