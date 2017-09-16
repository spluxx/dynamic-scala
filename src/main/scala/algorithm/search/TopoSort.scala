package algorithm.search

import structure.typeclass.{Edge, Graph, Node}
import structure.typeclass.Graph._

import scala.language.higherKinds

trait TopoSort {
  def topoSort[I, G[_, _]](
    nodes: List[Node[I]],
    edges: List[Edge[I, Unit]]
    )(implicit graph: Graph[G, I, Unit]): List[I] = {

    val G = Graph.makeGraph(nodes, edges)

    val initialOutSize = nodes.map { node =>
      val outCount = G(node.index).succ.size
      (node, outCount)
    }.toMap

    def step(
      leaves: List[Node[I]],
      outCount: Map[Node[I], Int],
      topo: List[Node[I]]
      ): (List[Node[I]], Map[Node[I], Int], List[Node[I]]) = {

      val aStepForward: (List[Node[I]], Map[Node[I], Int], List[Node[I]]) =
        leaves.foldLeft((List.empty[Node[I]], outCount, topo)) { (p, leaf) =>
          G(leaf.index).pred.foldLeft((List.empty[Node[I]], p._2, p._3)) { (ip, par) =>
            val outFromPar = ip._2(par._1)
            if(outFromPar == 1) (par._1 :: ip._1, ip._2.updated(par._1, 0), par._1 :: ip._3)
            else (ip._1, ip._2.updated(par._1, outFromPar - 1), ip._3)
          }
        }

      if(leaves.nonEmpty) step(aStepForward._1, aStepForward._2, aStepForward._3)
      else aStepForward
    }

    step(
      initialOutSize.filter(_._2 == 0).keys.toList,
      initialOutSize,
      initialOutSize.filter(_._2 == 0).keys.toList
    )._3.map(_.index).reverse
  }
}
