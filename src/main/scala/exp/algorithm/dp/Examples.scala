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


}

