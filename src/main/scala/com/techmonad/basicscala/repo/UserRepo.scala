package com.techmonad.basicscala.repo

import java.util.UUID

import com.techmonad.basicscala.domain.User
import com.techmonad.basicscala.repo.DataAccess.DatabaseConnection
import com.techmonad.basicscala.util.Mappable
import com.techmonad.basicscala.util.MappableConversion._

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

object DataAccess {
  type DatabaseConnection = ListBuffer[User]

  def uuid: UUID                     = UUID.randomUUID()
  val connection: DatabaseConnection = ListBuffer(User(uuid, "A", "a@a.com"), User(uuid, "B", "b@b.com"), User(uuid, "C", "c@c.com"))
}

abstract class UserRepo[F[_]: Mappable] {
  def all: Unit => F[List[User]]
  def find: UUID => F[Option[User]]
  def insert: User => F[User]
}

class UserRepoInterpreter(connection: DatabaseConnection) extends UserRepo {
  override def all: Unit => Future[List[User]] = _ => Future.successful(connection.toList)

  override def find: UUID => Future[Option[User]] = uuid => Future.successful(connection.find(u => u.id == uuid))

  override def insert: User => Future[User] = user => Future.successful { connection += user; user }
}

object UserRepo {
  def apply(): UserRepo[Future] = new UserRepoInterpreter(DataAccess.connection)
}
