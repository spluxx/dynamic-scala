import structure.typeclass.{Edge, Node}
import algorithm.search._

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
      Nil

    val edges: List[Edge[String, Unit]] =
      Edge(Node("one"), Node("two"), ()) ::
      Edge(Node("one"), Node("three"), ()) ::
      Edge(Node("three"), Node("four"), ()) ::
      Edge(Node("three"), Node("five"), ()) ::
      Edge(Node("three"), Node("six"), ()) ::
      Edge(Node("one"), Node("six"), ()) ::
      Edge(Node("two"), Node("seven"), ()) ::
      Edge(Node("two"), Node("four"), ()) ::
      Nil

    println(topoSort(nodes, edges))
  }
}
