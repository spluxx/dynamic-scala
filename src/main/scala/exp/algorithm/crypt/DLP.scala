package exp.algorithm.crypt

import exp.algorithm._

trait DLP {
  // Shank's baby step giant step algorithm to find discrete logarithm
  // Time Complexity: O(nlog(n)) where n = sqrt(mod)
  def dLog(pRoot: Long, h: Long, mod: Long): Long = {
    val n = Math.sqrt(mod.doubleValue).toLong + 1

    val (babySteps, gNth) =
      init(
        Vector.empty[(Long, Long)],
        1L
      ).loop(1L to n) { (babySteps, prev, idx) =>
        val r = (prev * pRoot) % mod
        ((r -> idx) +: babySteps, r)
      }

    val sortedBabySteps = babySteps.sortBy(_._1)

    val gNthInv = BigInt(gNth).modInverse(mod)

    init(
      -1L, BigInt(h)
    ).loop(1L to n) { (x, prev, idx) =>
      val giantStep = ((gNthInv * prev) % mod).toLong

      val binSearchIdx =
        init(
          0L, n-1
        ).loop { (l, r) =>
          if (l >= r) Break
          else {
            val m = (l & r) + ((l ^ r) >> 1)
            if (sortedBabySteps(m.toInt)._1 < giantStep) Next(m+1, r)
            else Next(l, m)
          }
        }._2

      val binSearchRes = sortedBabySteps(binSearchIdx.toInt)

      if (binSearchRes._1 == giantStep) {
        (binSearchRes._2 + n * idx, giantStep)
      } else (x, giantStep)
    }._1
  }
}
