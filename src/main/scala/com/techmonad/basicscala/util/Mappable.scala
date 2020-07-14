package com.techmonad.basicscala.util

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Mappable[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object Mappable {
  def apply[F[_]](implicit ev: Mappable[F]): Mappable[F] = ev

  implicit class MappableOps[F[_]: Mappable, A](f: F[A]) {
    def map[B](fab: A => B): F[B]        = Mappable[F].map(f)(fab)
    def flatMap[B](fab: A => F[B]): F[B] = Mappable[F].flatMap(f)(fab)
  }
}

object MappableConversion {

  implicit def futureMappable: Mappable[Future] = new Mappable[Future] {
    def map[A, B](fa: Future[A])(f: A => B): Future[B]             = fa.map(f)
    def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa.flatMap(f)
  }

}
