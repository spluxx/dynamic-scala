package exp.algorithm.graph

import exp.structure.typeclass.{Edge, Graph, Node}
import exp.algorithm._

import scala.collection.immutable.Queue
import scala.language.higherKinds

trait MaxFlow {
  def maxFlow[I, W: Numeric, G[_, _]](
    nodes: List[Node[I]],
    edges: List[Edge[I, W]],
    source: I,
    sink: I
    )(implicit graph: Graph[G, I, W]): (G[I, W], W) = {
    def num: Numeric[W] = implicitly[Numeric[W]]
    def cmp(w1: W, w2: W): Int = num.compare(w1, w2)

    val mapper =
      init(
        Map.empty[I, Int],
        0
      ).loop(nodes) { (map, n, node) =>
        map.get(node.index) match {
          case Some(_) => (map, n)
          case None => (map.+(node.index -> n), n+1)
        }
      }._1

    val capacity: Vector[Vector[W]] =
      init(
        Vector.fill(nodes.size)(Vector.fill(nodes.size)(num.zero))
      ).loop(edges) { (seq, edge) =>
        val from: Int = mapper(edge.from.index)
        val to: Int = mapper(edge.to.index)
        seq.updated(from, seq(from).updated(to, edge.weight))
      }

    val flow =
      init(
        capacity,
        num.zero
      ).loopTilBreak { (cap, maxFlow) =>
        val pathFinder =
          init(Map(source -> true).withDefault(_ => false), Queue(source), List((source, source, num.zero)))
            .loopTilBreak { (visited, queue, col) =>
              if(queue.size <= 0) Break
              else if(queue.head == sink) Break
              else {
                val (head, tails) = queue.dequeue
                val canGoto =
                  nodes.filter { next =>
                    !visited(next.index) &&
                    cmp(cap(mapper(head))(mapper(next.index)), num.zero) > 0
                  }.map { next =>
                    next.index ->
                    cap(mapper(head))(mapper(next.index))
                  }

                Next(
                  visited.++(canGoto.map(_._1 -> true)),
                  tails.enqueue(canGoto.map(_._1)),
                  canGoto.map(next => (head, next._1, next._2)) ::: col
                )
              }
            }

        if(!pathFinder._1(sink)) Break
        else {
          val extractedPath =
            pathFinder._3.foldLeft(sink, List((sink, sink, num.zero))) { case ((next, path), (from, to, c)) =>
              if(to == next) (from, (from, to, c) :: path)
              else (next, path)
            }._2

          val supremum =
            extractedPath.foldLeft(num.zero) { case (sup, (_, _, w)) =>
              if(sup == num.zero) w
              else if(w == num.zero) sup
              else num.min(sup, w)
            }

          Next {
            extractedPath.foldLeft(cap) { case (newCap, (f, t, _)) =>
              val from = mapper(f)
              val to = mapper(t)
              if(from == to) newCap
              else newCap
                .updated(from, newCap(from).updated(to, num.minus(newCap(from)(to), supremum)))
                .updated(to, newCap(to).updated(from, num.plus(newCap(to)(from), supremum)))
            } -> num.plus(maxFlow, supremum)
          }
        }
      }

    val flowEdges =
      nodes.flatMap(a => nodes.map(b => (a, b))).map { case (from, to) =>
        Edge(from, to,
          num.minus(
            capacity(mapper(from.index))(mapper(to.index)),
            flow._1(mapper(from.index))(mapper(to.index))
          )
        )
      }.filter(p => cmp(p.weight, num.zero) > 0)

    (graph.makeGraph(nodes, flowEdges), flow._2)
  }
}
