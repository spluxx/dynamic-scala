package exp.structure.instance

import exp.structure.datatype.DirectedGraph
import exp.structure.typeclass.{Edge, Graph, Node, NodeCtx}

trait GraphInstance {
  implicit def directedGraph[I, W]: Graph[DirectedGraph, I, W] = new Graph[DirectedGraph, I, W] {

    override def empty: DirectedGraph[I, W] = DirectedGraph.empty

    override def isEmpty(graph: DirectedGraph[I, W]): Boolean = graph.isEmpty

    override def apply(graph: DirectedGraph[I, W])(index: I): NodeCtx[I, W] = graph(index)

    override def makeGraph(nodes: List[Node[I]], edges: List[Edge[I, W]]): DirectedGraph[I, W] =
      DirectedGraph.makeGraph(nodes, edges)

    override def nodes(graph: DirectedGraph[I, W]): List[Node[I]] = graph.nodes
  }
}
