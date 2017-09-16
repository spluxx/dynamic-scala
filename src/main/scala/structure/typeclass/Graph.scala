package structure.typeclass

import scala.language.{higherKinds, implicitConversions}

// reference: http://labra.github.io/wesin/papers/InductiveTripleGraphs.pdf

case class Node[I](index: I)

case class NodeCtx[I, W](pred: List[(Node[I], W)], node: Node[I], succ: List[(Node[I], W)])

case class Edge[I, W](from: Node[I], to: Node[I], weight: W)

trait Graph[G[_, _], I, W] {
  def empty: G[I, W]
  def isEmpty(graph: G[I, W]): Boolean
  def apply(graph: G[I, W])(index: I): NodeCtx[I, W]
  def makeGraph(nodes: List[Node[I]], edges: List[Edge[I, W]]): G[I, W]
  def nodes(graph: G[I, W]): List[Node[I]]
}

object Graph extends GraphTypeClass {
  def makeGraph[G[_, _], I, W](nodes: List[Node[I]], edges: List[Edge[I, W]])(implicit graph: Graph[G, I, W]): G[I, W] =
    graph.makeGraph(nodes, edges)

  def empty[G[_, _], I, W](implicit graph: Graph[G, I, W]): G[I, W] =
    graph.empty
}

trait GraphTypeClass {
  trait GraphOps[G[_, _], I, W] {
    def tcInstance: Graph[G, I, W]
    def target: G[I, W]

    def isEmpty: Boolean = tcInstance.isEmpty(target)
    def apply(index: I): NodeCtx[I, W] = tcInstance(target)(index)
    def nodes: List[Node[I]] = tcInstance.nodes(target)
  }

  implicit def graphToGraphOps[G[_, _], I, W](tg: G[I, W])(implicit graph: Graph[G, I, W]): GraphOps[G, I, W] =
    new GraphOps[G, I, W] {
      def tcInstance: Graph[G, I, W] = graph
      def target: G[I, W] = tg
    }
}
