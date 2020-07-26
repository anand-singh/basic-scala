package com.techmonad.basicscala.service

import java.util.UUID

import com.techmonad.basicscala.domain.Request.{HttpRequest, HttpResponse, NewUser}
import com.techmonad.basicscala.domain.User
import com.techmonad.basicscala.repo.UserRepo
import com.techmonad.basicscala.util.{Mappable, ScalaLogger}
import io.scalaland.chimney.dsl._

import scala.util.chaining._

class UserService[F[_]: Mappable](repo: UserRepo[F]) extends ScalaLogger {
  type WebRequest[A, B] = HttpRequest[A] => F[HttpResponse[B]]
  type TransformRes[A]  = F[A] => F[HttpResponse[A]]
  type TransformReq[A]  = HttpRequest[A] => A

  import Mappable._

  def findAllUsers: WebRequest[Unit, List[User]] = transformReq[Unit] andThen repo.all andThen transformRes[List[User]]
  def findUserById: WebRequest[UUID, Option[User]] = transformReq[UUID] andThen repo.find andThen transformRes[Option[User]]
  def registerUser: WebRequest[NewUser, User] = transformReq[NewUser] andThen toUser andThen repo.insert andThen transformRes[User]

  //Register User
  val toUser: NewUser => User = _.into[User].withFieldComputed(_.id, _ => UUID.randomUUID()).transform

  //Transform Req/Res
  private def transformReq[A]: TransformReq[A] = _.tap(r => info(s"Request received ID - [${r.requestId}]")).body
  private def transformRes[A]: TransformRes[A] = _.map(b => HttpResponse[A](UUID.randomUUID(), b).tap(r => info(s"Response - [$r]")))
}



object UserService {
  def apply[F[_]: Mappable](repo: UserRepo[F]): UserService[F] = new UserService(repo)
}
