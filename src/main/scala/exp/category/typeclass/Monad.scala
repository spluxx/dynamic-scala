package exp.category.typeclass

import scala.language.{higherKinds, implicitConversions}

trait Monad[F[_]] extends Functor[F] {
  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  def pure[T](t: T): F[T]
}

object Monad {
  def pure[T, M[_]: Monad](t: T): M[T] = implicitly[Monad[M]].pure(t)

  implicit class monadOps[A, M[_]: Monad](target: M[A]) {
    def map[B](f: A => B): M[B] = implicitly[Monad[M]].map(target)(f)
    def flatMap[B](f: A => M[B]): M[B] = implicitly[Monad[M]].flatMap(target)(f)
  }
}
