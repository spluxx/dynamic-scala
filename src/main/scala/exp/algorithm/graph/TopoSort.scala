package exp.algorithm.graph

import exp.algorithm._
import exp.structure.typeclass.Graph._
import exp.structure.typeclass.{Edge, Graph, Node}

import scala.language.higherKinds

trait TopoSort {
  def topoSort[I, G[_, _]](
    nodes: List[Node[I]],
    edges: List[Edge[I, Unit]]
    )(implicit graph
    : Graph[G, I, Unit]): List[I] = {

    val G = Graph.makeGraph(nodes, edges)

    val initialOutSize = nodes.map { node =>
      (node, G(node.index).succ.size)
    }.toMap

    val initialLeaves = initialOutSize.filter(_._2 == 0).keys.toList

    init(
      initialOutSize,
      initialLeaves,
      initialLeaves
    ).loop { (outCount, leaves, sorted) =>

      init(
        outCount,
        List.empty[Node[I]],
        sorted
      ).loop(leaves.flatMap(leaf => G(leaf.index).pred.map(_._1))) { (outCountAux, newLeaves, sortedAux, parent) =>
        outCountAux(parent) match {
          case 1 => (outCountAux.updated(parent, 0), parent :: newLeaves, parent :: sortedAux)
          case r => (outCountAux.updated(parent, r - 1), newLeaves, sortedAux)
        }
      } match {
        case (_, Nil, _) => Break
        case (cnt, xs, srt) => Next(cnt, xs, srt)
      }

    }._3.map(_.index).reverse
  }
}
