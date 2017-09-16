package algorithm.search

import org.scalatest.{FlatSpec, Matchers}
import structure.typeclass.{Edge, Graph, Node}
import structure.typeclass.Graph._
import structure._

import scala.collection.mutable
import scala.io.Source
import scala.language.higherKinds

class TopoSortSpec extends FlatSpec with Matchers {
  def check[G[_, _], I, W](gr: G[I, W], answer: List[I])(implicit graph: Graph[G, I, W]): Boolean = {
    val vis: mutable.ArrayBuffer[Boolean] = mutable.ArrayBuffer().++=(List.fill(gr.nodes.size)(false))
    val mapper = gr.nodes.foldLeft((0, Map.empty[I, Int])) { (p, node) =>
      (p._1+1, p._2.+(node.index -> p._1))
    }._2

    (for {
      elm <- answer
    } yield {
      val isEveryChildVisisted = gr(elm).succ.forall(child => vis(mapper(child._1.index)))

      if (isEveryChildVisisted) vis.update(mapper(elm), true)

      isEveryChildVisisted
    }).forall(identity)
  }

  def loadResource(filePath: String): (List[Node[String]], List[Edge[String, Unit]]) = {
    val br = Source.fromResource(filePath).bufferedReader
    val nodeBuff: mutable.ArrayBuilder[String] = mutable.ArrayBuilder.make()
    val edgeBuff: mutable.ArrayBuilder[(String, String)] = mutable.ArrayBuilder.make()

    var n = Integer.valueOf(br.readLine)
    while(n > 0) {
      nodeBuff.+=(br.readLine)
      n = n - 1
    }
    var m = Integer.valueOf(br.readLine)
    while(m > 0) {
      val s = br.readLine.split(" ")
      edgeBuff.+=(s(0) -> s(1))
      m = m - 1
    }
    br.close()

    nodeBuff.result().toList.map(s => Node(s)) ->
    edgeBuff.result().toList.map(p => Edge(Node(p._1), Node(p._2), ()))
  }

  "check" should "judge toposort correctly " in {
    val testCase: (List[Node[String]], List[Edge[String, Unit]]) = loadResource("toposort/ts1.in")

    val pseudoAnswer: List[String] =
      "zero" :: "seven"  :: "four"   :: "six" :: "five"   ::
      "two"  :: "nine"   :: "eight"  :: "one" :: "three"  :: Nil

    check(Graph.makeGraph(testCase._1, testCase._2), pseudoAnswer) should be(false)
  }

  "toposort" should "accurately compute the topological order" in {
    val testCase = loadResource("toposort/ts2.in")
    val soln = topoSort(testCase._1, testCase._2)
    check(Graph.makeGraph(testCase._1, testCase._2), soln) should be(true)
  }

  it should "accurately compute the topological order (2)" in {
    val testCase = loadResource("toposort/ts3.in")
    val soln = topoSort(testCase._1, testCase._2)
    check(Graph.makeGraph(testCase._1, testCase._2), soln) should be(true)
  }

  it should "accurately compute the topological order (3)" in {
    val testCase = loadResource("toposort/ts4.in")
    val soln = topoSort(testCase._1, testCase._2)
    check(Graph.makeGraph(testCase._1, testCase._2), soln) should be(true)
  }

  it should "accurately compute the topological order (4)" in {
    val testCase = loadResource("toposort/ts5.in")
    val soln = topoSort(testCase._1, testCase._2)
    check(Graph.makeGraph(testCase._1, testCase._2), soln) should be(true)
  }
}
