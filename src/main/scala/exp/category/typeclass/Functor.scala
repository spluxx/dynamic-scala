package exp.category.typeclass

import scala.language.{higherKinds, implicitConversions}

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  implicit class functorOps[A, F[_]: Functor](target: F[A]) {
    def map[B](f: A => B): F[B] = implicitly[Functor[F]].map(target)(f)
  }
}
