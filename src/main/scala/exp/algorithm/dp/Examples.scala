package exp.algorithm.dp

import exp.category.datatype.State
import exp.category.datatype.StateT.State
import exp.category.Implicits._

object Examples {
  def fib(n: Int): State[Map[Int, BigInt], BigInt] = State { s =>
    s.get(n) match {
      case Some(r) => (s, r)
      case None =>
        val res = (for {
          a <- fib(n-1)
          b <- fib(n-2)
        } yield a + b).eval(s)
        (res._1.+(n -> res._2), res._2)
    }
  }
}

