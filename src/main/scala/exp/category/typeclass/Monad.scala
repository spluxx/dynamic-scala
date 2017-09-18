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

  trait Ops[M[_], A] {
    def target: M[A]
    def tcInstance: Monad[M]

    def map[B](f: A => B): M[B] = tcInstance.map(target)(f)
    def flatMap[B](f: A => M[B]): M[B] = tcInstance.flatMap(target)(f)
  }

  implicit def monadOps[A, M[_]: Monad](tg: M[A]): Ops[M, A] = new Ops[M, A] {
    override def target: M[A] = tg
    override def tcInstance: Monad[M] = implicitly[Monad[M]]
  }
}
