package exp.category.datatype

import exp.category.datatype.Id.Id
import exp.category.datatype.StateT.State
import exp.category.typeclass.Functor._
import exp.category.typeclass.Monad._
import exp.category.typeclass.{Functor, Monad}

import scala.language.higherKinds

class StateT[F[_], S, A](val eval: S => F[(S, A)]) { self =>
  def map[B](f: A => B)(implicit functor: Functor[F]): StateT[F, S, B] =
    StateT(s => self.eval(s).map(p => (p._1, f(p._2))))

  def flatMap[B](f: A => StateT[F, S, B])(implicit monad: Monad[F]): StateT[F, S, B] =
    StateT(s => self.eval(s).flatMap(p => f(p._2).eval(p._1)))
}

object StateT {
  def apply[F[_], S, A](eval: S => F[(S, A)]): StateT[F, S, A] = new StateT(eval)

  def pure[F[_]: Monad, S, A](a: A): StateT[F, S, A] =
    StateT(s => Monad.pure[(S, A), F]((s, a)))

  def set[F[_]: Monad, S](s: S): StateT[F, S, Unit] =
    StateT(_ => Monad.pure[(S, Unit), F]((s, ())))

  def get[F[_]: Monad, S]: StateT[F, S, S] =
    StateT(s => Monad.pure[(S, S), F]((s, s)))

  type State[S, A] = StateT[Id, S, A]
}

object State {
  def apply[S, A](eval: S => (S, A)): State[S, A] = new StateT[Id, S, A](eval)

  def pure[S, A](a: A): State[S, A] =
    State(s => (s, a))

  def set[S](s: S): State[S, Unit] =
    State(_ => (s, ()))

  def get[S]: State[S, S] =
    State(s => (s, s))
}
