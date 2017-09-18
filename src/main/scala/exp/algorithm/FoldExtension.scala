package exp.algorithm

trait FoldExtension {
  def init[T1](t1: T1): Suspend1[T1] = Suspend1(t1)
  def init[T1, T2](t1: T1, t2: T2): Suspend2[T1, T2] = Suspend2(t1, t2)
  def init[T1, T2, T3](t1: T1, t2: T2, t3: T3): Suspend3[T1, T2, T3] = Suspend3(t1, t2, t3)

  sealed trait Control[+T]
  case class Next[T](value: T) extends Control[T]
  case object Break extends Control[Nothing]

  // not exactly a "fix", but gives you an idea
  def fix[T](t: T)(f: T => Control[T]): T = {
    f(t) match {
      case Next(nt) => fix(nt)(f)
      case Break => t
    }
  }

  sealed trait Suspend

  case class Suspend1[T1](private val t1: T1) extends Suspend {
    def loop[U](around: Seq[U])(f: (T1, U) => T1): T1 =
      around.foldLeft(t1)(f)

    def loopTilBreak[U](f: T1 => Control[T1]): T1 = fix(t1)(f)
  }

  case class Suspend2[T1, T2](private val t1: T1, private val t2: T2) extends Suspend {
    def loop[U](around: Seq[U])(f: (T1, T2, U) => (T1, T2)): (T1, T2) =
      around.foldLeft(t1, t2)((ts, u) => f(ts._1, ts._2, u))

    def loopTilBreak[U](f: (T1, T2) => Control[(T1, T2)]): (T1, T2) = fix(t1, t2)(f.tupled)
  }

  case class Suspend3[T1, T2, T3](private val t1: T1, private val t2: T2, private val t3: T3) extends Suspend {
    def loop[U](around: Seq[U])(f: (T1, T2, T3, U) => (T1, T2, T3)): (T1, T2, T3) =
      around.foldLeft(t1, t2, t3)((ts, u) => f(ts._1, ts._2, ts._3, u))

    def loopTilBreak[U](f: (T1, T2, T3) => Control[(T1, T2, T3)]): (T1, T2, T3) = fix(t1, t2, t3)(f.tupled)
  }
}
