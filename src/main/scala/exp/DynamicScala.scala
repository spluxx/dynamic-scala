package exp

import exp.algorithm.graph._
import exp.algorithm.crypt._
import exp.algorithm.dp.Examples._
import exp.structure.Implicits._
import exp.structure.typeclass.{Edge, Node}


object DynamicScala {
  def main(args: Array[String]): Unit = {
    println(dLog(112, 5, 200000000001419L))
  }
}
