package exp.algorithm.dp

import exp.algorithm._

object Examples {
  def fib(n: Int): BigInt = {
    init(
      Map(
        0 -> BigInt(1),
        1 -> BigInt(1)
      )
    ).loop(2 to n) { (map, i) =>
      map.+((i, map(i-1)+map(i-2)))
    }(n)
  }

  def isPrime(p: Int): Boolean = {
    init(
      true
    ).loop(2 to Math.sqrt(p).toInt) { (q, i) =>
      q && !(p % i == 0)
    }
  }

  def sqrt(square: Double): Double = {
    init(
      square,
      1.0
    ).loop { (x, y) =>
      if(math.abs((y*y-x)/x) > 1e-6) Next(x, ((x/y)+y)/2)
      else Break
    }._2
  }

  def binSearch(list: Vector[Int], find: Int): Int = {
    init(
      0, list.length
    ).loop { (l, r) =>
      if (l >= r) Break
      else {
        val m = (l & r) + ((l ^ r) >> 1)
        if (list(m) < find) Next(m + 1, r)
        else Next(l, m)
      }
    }._2
  }
}

