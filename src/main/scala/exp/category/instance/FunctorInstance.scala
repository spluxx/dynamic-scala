package exp.category.instance

import exp.category.datatype.Id.Id
import exp.category.datatype.StateT
import exp.category.typeclass.Functor
import monix.eval.Task

import scala.concurrent.{ExecutionContext, Future}

trait FunctorInstance {
  implicit def idFunctor: Functor[Id] = new Functor[Id] {
    override def map[A, B](fa: Id[A])(f: A => B): Id[B] = f(fa)
  }

  implicit def listFunctor: Functor[List] = new Functor[List] {
    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  implicit def optionFunctor: Functor[Option] = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

  implicit def futureFunctor(implicit ec: ExecutionContext): Functor[Future] = new Functor[Future] {
    override def map[A, B](fa: Future[A])(f: A => B): Future[B] = fa.map(f)(ec)
  }

  implicit def taskFunctor: Functor[Task] = new Functor[Task] {
    override def map[A, B](fa: Task[A])(f: A => B): Task[B] = fa.map(f)
  }

  implicit def stateFunctor[S, F[_]: Functor]: Functor[StateT[F, S, ?]] = new Functor[StateT[F, S, ?]] {
    override def map[A, B](fa: StateT[F, S, A])(f: (A) => B): StateT[F, S, B] = fa.map(f)
  }
}

