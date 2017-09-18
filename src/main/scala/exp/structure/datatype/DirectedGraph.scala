package exp.structure.datatype

import exp.structure.typeclass.{Edge, Node, NodeCtx}

trait DirectedGraph[@specialized(Int) I, W] {
  def nodes: List[Node[I]]
  def nodeMap: Map[I, NodeCtx[I, W]]
  def apply(index: I): NodeCtx[I, W] = nodeMap(index)
  def isEmpty: Boolean = nodeMap.isEmpty
}

object DirectedGraph {
  def empty[I, W]: DirectedGraph[I, W] = makeGraph(List.empty, List.empty)

  def makeGraph[I, W](nodeList: List[Node[I]], edgeList: List[Edge[I, W]]): DirectedGraph[I, W] = new DirectedGraph[I, W] {
    override lazy val nodes: List[Node[I]] = nodeList
    override lazy val nodeMap: Map[I, NodeCtx[I, W]] = {
      val out = edgeList.groupBy(_.from).mapValues(_.map(p => p.to -> p.weight))
      val in = edgeList.groupBy(_.to).mapValues(_.map(p => p.from -> p.weight))

      nodeList.foldLeft(Map.empty[I, NodeCtx[I, W]]) { (map, node) =>
        map + {
          node.index ->
            NodeCtx(
              in.getOrElse(node, List.empty),
              node,
              out.getOrElse(node, List.empty)
            )
        }
      }
    }
  }
}

