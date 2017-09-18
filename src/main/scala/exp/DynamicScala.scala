package exp

import exp.algorithm.search.topoSort
import exp.structure.typeclass.{Edge, Graph, Node}
import exp.structure.Implicits.directedGraph


object DynamicScala {
  def main(args: Array[String]): Unit = {
    val nodes: List[Node[String]] =
      Node("one") ::
      Node("two") ::
      Node("three") ::
      Node("four") ::
      Node("five") ::
      Node("six") ::
      Node("seven") ::
      Node("eight") ::
      Node("nine") ::
      Nil

    val edges: List[Edge[String, Unit]] =
      Edge(Node("two"), Node("one"), ()) ::
      Edge(Node("three"), Node("one"), ()) ::
      Edge(Node("four"), Node("one"), ()) ::
      Edge(Node("five"), Node("one"), ()) ::
      Edge(Node("six"), Node("two"), ()) ::
      Edge(Node("seven"), Node("two"), ()) ::
      Edge(Node("eight"), Node("two"), ()) ::
      Edge(Node("nine"), Node("two"), ()) ::
      Edge(Node("nine"), Node("three"), ()) ::
      Edge(Node("nine"), Node("four"), ()) ::
      Edge(Node("nine"), Node("five"), ()) ::
      Edge(Node("nine"), Node("six"), ()) ::
      Nil

    println(topoSort(nodes, edges))
  }
}
