package exp.algorithm.graph

import exp.algorithm._
import exp.category.datatype.State
import exp.category.datatype.StateT.State
import exp.structure.typeclass.Graph._
import exp.structure.typeclass.{Edge, Graph, Node}

import scala.collection.immutable.Queue
import scala.language.higherKinds

trait TopoSort {
  def topoSort[I, G[_, _]](
    nodes: List[Node[I]],
    edges: List[Edge[I, Unit]]
    )(implicit graph : Graph[G, I, Unit]): List[I] = {

    val G = Graph.makeGraph(nodes, edges)

    val initialOutSize = nodes.map { node =>
      (node, G(node.index).succ.size)
    }.toMap

    val initialLeaves = initialOutSize.filter(_._2 == 0).keys.toList

    init(initialOutSize, initialLeaves, initialLeaves)
      .loop { (outCount, leaves, sorted) =>
        init(outCount, List.empty[Node[I]], sorted)
          .loop(leaves.flatMap(leaf => G(leaf.index).pred.map(_._1))) { (outCountAux, newLeaves, sortedAux, parent) =>
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

  def stateTopoSort[I, G[_, _]](
                            nodes: List[Node[I]],
                            edges: List[Edge[I, Unit]]
                          )(implicit graph : Graph[G, I, Unit]): List[I] = {
    type S = (Map[Node[I], Int], Queue[Node[I]], List[Node[I]])

    val G = Graph.makeGraph(nodes, edges)

    val initialOutSize = nodes.map { node =>
      (node, G(node.index).succ.size)
    }.toMap

    val initialLeaves = initialOutSize.filter(_._2 == 0).keys.toList

    val aStep: State[S, Boolean] = State { case (outCount, leaves, sorted) =>
      leaves.dequeueOption match {
        case Some((leaf, tail)) =>
          val updatedParents =
            G(leaf.index).pred.foldLeft(outCount, Queue.empty[Node[I]], sorted) {
              case ((oc, newLeaves, sortedAux), (par, _)) =>
                oc(par) match {
                  case 1 => (oc.updated(par, 0), newLeaves.enqueue(par), par :: sortedAux)
                  case r => (oc.updated(par, r - 1), newLeaves, sortedAux)
                }
            }
          ((updatedParents._1, tail.enqueue(updatedParents._2), updatedParents._3), true)
        case None => ((outCount, leaves, sorted), false)
      }
    }

    def steps(s: S)(stm: State[S, Boolean]): List[I] = {
      stm.eval(s) match {
        case ((outCount, leaves, sorted), true) => steps(outCount, leaves, sorted)(stm)
        case ((_, _, sorted), false) => sorted.map(_.index)
      }
    }

    steps(initialOutSize, Queue.empty.enqueue(initialLeaves), initialLeaves)(aStep).reverse
  }
}
