package exp.category.instance

import exp.category.datatype.Id.Id
import exp.category.datatype.StateT
import exp.category.typeclass.Monad
import monix.eval.Task

import scala.concurrent.{ExecutionContext, Future}

trait MonadInstance {
  implicit def idMonad: Monad[Id] = new Monad[Id] {
    override def pure[T](t: T): Id[T] = t
    override def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)
  }

  implicit def listMonad: Monad[List] = new Monad[List] {
    override def pure[T](t: T): List[T] = t :: Nil
    override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
  }

  implicit def optionMonad: Monad[Option] = new Monad[Option] {
    override def pure[T](t: T): Option[T] = Some(t)
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
  }

  implicit def futureMonad(implicit ec: ExecutionContext): Monad[Future] = new Monad[Future] {
    override def pure[T](t: T): Future[T] = Future.successful(t)
    override def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa.flatMap(f)(ec)
  }

  implicit def taskMonad: Monad[Task] = new Monad[Task] {
    override def pure[T](t: T): Task[T] = Task.pure(t)
    override def flatMap[A, B](fa: Task[A])(f: A => Task[B]): Task[B] = fa.flatMap(f)
  }

  implicit def stateMonad[S, F[_]: Monad]: Monad[StateT[F, S, ?]] = new Monad[StateT[F, S, ?]] {
    override def pure[T](t: T): StateT[F, S, T] = StateT.pure(t)
    override def flatMap[A, B](fa: StateT[F, S, A])(f: A => StateT[F, S, B]): StateT[F, S, B] = fa.flatMap(f)
  }
}

