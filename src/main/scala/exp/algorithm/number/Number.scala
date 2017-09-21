package exp.algorithm.number

trait Number {
  def gcduv(a: BigInt, b: BigInt): (BigInt, BigInt, BigInt) = {
    def gcduvAux(u: BigInt, g: BigInt, x: BigInt, y: BigInt): (BigInt, BigInt, BigInt) = {
      if(y == 0) (g, u, (g-a*u)/b)
      else {
        val q = g/y
        val t = g%y
        val s = u-q*x
        gcduvAux(x, y, s, t)
      }
    }
    gcduvAux(1, a, 0, b)
  }
}
