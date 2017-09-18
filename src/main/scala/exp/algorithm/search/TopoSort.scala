package exp.algorithm.search

import exp.structure.typeclass.{Edge, Graph, Node}
import exp.structure.typeclass.Graph._
import exp.algorithm._

import scala.language.higherKinds

trait TopoSort {
  def topoSort[I, G[_, _]](
                            nodes: List[Node[I]],
                            edges: List[Edge[I, Unit]]
                          )(implicit graph: Graph[G, I, Unit]): List[I] = {

    val G = Graph.makeGraph(nodes, edges)

    val initialOutSize = nodes.map { node =>
      (node, G(node.index).succ.size)
    }.toMap

    val initialLeaves = initialOutSize.filter(_._2 == 0).keys.toList

    init(
      initialOutSize,
      initialLeaves,
      initialLeaves
    ).loopTilBreak { (outCount, leaves, sorted) =>

      val newOutCount =
       init(
          outCount,
          List.empty[Node[I]],
          sorted
        ).loop(leaves.flatMap(leaf => G(leaf.index).pred.map(_._1))) { (cnt, newLeaves, srted, node) =>
          cnt(node) match {
            case 1 => (cnt.updated(node, 0), node :: newLeaves, node :: srted)
            case r => (cnt.updated(node, r - 1), newLeaves, srted)
          }
        }

      newOutCount._2 match {
        case Nil => Break
        case xs => Next((newOutCount._1, xs, newOutCount._3))
      }
    }._3.map(_.index).reverse
  }
}
