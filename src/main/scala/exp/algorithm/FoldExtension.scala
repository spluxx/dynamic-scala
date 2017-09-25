package exp.algorithm

import scala.annotation.tailrec

trait FoldExtension {
  def init[T1](t1: T1): Suspend1[T1] =
    Suspend1(t1)
  def init[T1, T2](t1: T1, t2: T2): Suspend2[T1, T2] =
    Suspend2(t1, t2)
  def init[T1, T2, T3](t1: T1, t2: T2, t3: T3): Suspend3[T1, T2, T3] =
    Suspend3(t1, t2, t3)
  def init[T1, T2, T3, T4](t1: T1, t2: T2, t3: T3, t4: T4): Suspend4[T1, T2, T3, T4] =
    Suspend4(t1, t2, t3, t4)
  def init[T1, T2, T3, T4, T5](t1: T1, t2: T2, t3: T3, t4: T4, t5: T5): Suspend5[T1, T2, T3, T4, T5] =
    Suspend5(t1, t2, t3, t4, t5)

  sealed trait Control[+T]
  case class Next[T](next: T) extends Control[T]
  case object Break extends Control[Nothing]

  @tailrec
  final def recurse[T](t: T)(f: T => Control[T]): T = {
    f(t) match {
      case Next(nt) => recurse(nt)(f)
      case Break => t
    }
  }

  sealed trait Suspend

  case class Suspend1[T1](private val t1: T1) extends Suspend {
    def loop[U](around: TraversableOnce[U])(f: (T1, U) => T1): T1 =
      around.foldLeft(t1)(f)

    def loop[U](f: T1 => Control[T1]): T1 = recurse(t1)(f)
  }

  case class Suspend2[T1, T2](private val t1: T1, private val t2: T2) extends Suspend {
    def loop[U](around: TraversableOnce[U])(f: (T1, T2, U) => (T1, T2)): (T1, T2) =
      around.foldLeft(t1, t2)((ts, u) => f(ts._1, ts._2, u))

    def loop[U](f: (T1, T2) => Control[(T1, T2)]): (T1, T2) =
      recurse(t1, t2)(f.tupled)
  }

  case class Suspend3[T1, T2, T3](private val t1: T1, private val t2: T2, private val t3: T3) extends Suspend {
    def loop[U](around: TraversableOnce[U])(f: (T1, T2, T3, U) => (T1, T2, T3)): (T1, T2, T3) =
      around.foldLeft(t1, t2, t3)((ts, u) => f(ts._1, ts._2, ts._3, u))

    def loop[U](f: (T1, T2, T3) => Control[(T1, T2, T3)]): (T1, T2, T3) =
      recurse(t1, t2, t3)(f.tupled)
  }

  case class Suspend4[T1, T2, T3, T4](
    private val t1: T1,
    private val t2: T2,
    private val t3: T3,
    private val t4: T4) extends Suspend {
    def loop[U](around: TraversableOnce[U])(f: (T1, T2, T3, T4, U) => (T1, T2, T3, T4)): (T1, T2, T3, T4) =
      around.foldLeft(t1, t2, t3, t4)((ts, u) => f(ts._1, ts._2, ts._3, ts._4, u))

    def loop[U](f: (T1, T2, T3, T4) => Control[(T1, T2, T3, T4)]): (T1, T2, T3, T4) =
      recurse(t1, t2, t3, t4)(f.tupled)
  }

  case class Suspend5[T1, T2, T3, T4, T5](
    private val t1: T1,
    private val t2: T2,
    private val t3: T3,
    private val t4: T4,
    private val t5: T5) extends Suspend {
    def loop[U](around: TraversableOnce[U])(f: (T1, T2, T3, T4, T5, U) => (T1, T2, T3, T4, T5)): (T1, T2, T3, T4, T5) =
      around.foldLeft(t1, t2, t3, t4, t5)((ts, u) => f(ts._1, ts._2, ts._3, ts._4, ts._5, u))

    def loop[U](f: (T1, T2, T3, T4, T5) => Control[(T1, T2, T3, T4, T5)]): (T1, T2, T3, T4, T5) =
      recurse(t1, t2, t3, t4, t5)(f.tupled)
  }
}
