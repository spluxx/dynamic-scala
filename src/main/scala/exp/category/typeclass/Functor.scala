package exp.category.typeclass

import scala.language.{higherKinds, implicitConversions}

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  trait Ops[F[_], A] {
    def target: F[A]
    def tcInstance: Functor[F]

    def map[B](f: A => B): F[B] = tcInstance.map(target)(f)
  }

  implicit def functorOps[A, F[_]: Functor](tg: F[A]): Ops[F, A] = new Ops[F, A] {
    override def target: F[A] = tg
    override def tcInstance: Functor[F] = implicitly[Functor[F]]
  }
}
