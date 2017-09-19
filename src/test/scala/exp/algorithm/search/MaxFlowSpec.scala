package exp.algorithm.search

import exp.structure.typeclass.{Edge, Graph, Node}
import exp.algorithm.graph._
import exp.structure.Implicits._
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable
import scala.io.Source
import scala.language.higherKinds

//class MaxFlowSpec extends FlatSpec with Matchers {
//  def check[G[_, _], I, W: Numeric](
//    gr: G[I, W],
//    flowGraph: G[I, W],
//    maxFlow: W
//    )(implicit graph: Graph[G, I, W]): Boolean = {
//
//    ???
//  }
//
//  def loadResource[W: Numeric](filePath: String): (List[Node[String]], List[Edge[String, W]], String, String) = {
//    val br = Source.fromResource(filePath).bufferedReader
//    val nodeBuff: mutable.ArrayBuilder[String] = mutable.ArrayBuilder.make()
//    val edgeBuff: mutable.ArrayBuilder[(String, String, W)] = mutable.ArrayBuilder.make()
//
//    var n = Integer.valueOf(br.readLine)
//    while(n > 0) {
//      nodeBuff.+=(br.readLine)
//      n = n - 1
//    }
//    var m = Integer.valueOf(br.readLine)
//    while(m > 0) {
//      val s = br.readLine.split(" ")
//      // test cases are in integer, but will change types
//      edgeBuff.+=((s(0), s(1), implicitly[Numeric[W]].fromInt(s(2).toInt)))
//      m = m - 1
//    }
//    val source = br.readLine.trim()
//    val sink = br.readLine.trim()
//    br.close()
//
//    (
//      nodeBuff.result().toList.map(s => Node(s)),
//      edgeBuff.result().toList.map(p => Edge(Node(p._1), Node(p._2), p._3)),
//      source,
//      sink
//    )
//  }
//
//  "check" should "judge maxflow correctly " in {
//    val testCase: (List[Node[String]], List[Edge[String, Int]], String, String) = loadResource("maxflow/ts1.in")
//
//    val pseudoAnswer: List[String] =
//      "zero" :: "seven"  :: "four"   :: "six" :: "five"   ::
//        "two"  :: "nine"   :: "eight"  :: "one" :: "three"  :: Nil
//
////    check(Graph.makeGraph(testCase._1, testCase._2), pseudoAnswer) should be(false)
//    ???
//  }
//
//  "maxflow" should "accurately compute the max flow, and its flow graph" in {
//    val testCase = loadResource("maxflow/ts2.in")
//    val soln = maxFlow(testCase._1, testCase._2, testCase._3, testCase._4)
//    check(Graph.makeGraph(testCase._1, testCase._2), soln._1, soln._2) should be(true)
//  }
//}

