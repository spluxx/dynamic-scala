package exp

import exp.algorithm.graph._
import exp.structure.Implicits._
import exp.structure.typeclass.{Edge, Node}

object DynamicScala {
  def main(args: Array[String]): Unit = {
    val nodes: List[Node[String]] =
      Node("X") ::
        Node("A") ::
        Node("B") ::
        Node("C") ::
        Node("D") ::
        Node("E") ::
        Node("Y") ::
        Nil

    val edges: List[Edge[String, Int]] =
      Edge(Node("X"), Node("A"), 3) ::
        Edge(Node("X"), Node("B"), 1) ::
        Edge(Node("B"), Node("C"), 5) ::
        Edge(Node("A"), Node("C"), 3) ::
        Edge(Node("B"), Node("D"), 4) ::
        Edge(Node("D"), Node("E"), 2) ::
        Edge(Node("E"), Node("Y"), 3) ::
        Edge(Node("D"), Node("E"), 3) ::
        Edge(Node("C"), Node("Y"), 2) ::
        Nil

    println(maxFlow(nodes, edges, "X", "Y"))
  }
}
