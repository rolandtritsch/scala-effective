import java.util.concurrent.Future
import wikigraph.implementations.Sqlite
import wikigraph.Articles.ArticleId
import wikigraph.Wikigraph

//import scala.concurrent.duration.DurationInt
//import scala.concurrent.{ Await, ExecutionContext }
//import ExecutionContext.Implicits.global

val wiki = Sqlite()
val scala = ArticleId(3254510)
val python = ArticleId(23862)
val assembly = ArticleId(1368)
val jvm = ArticleId(16389)
val analyser = Wikigraph(wiki)

//Await.result(Future(1), 1.minute)

//Await.result(wiki.nameOfArticle(scala).value, 2.minutes)
//Await.result(wiki.linksFrom(python).value, 10.minutes)
//Await.result(analyser.breadthFirstSearch(scala, assembly, 20).value, 20.minutes)
//Await.result(analyser.breadthFirstSearch(scala, jvm, 20).value, 20.minutes)
//Await.result(analyser.breadthFirstSearch(scala, python, 20).value, 20.minutes)
//Await.result(analyser.breadthFirstSearch(python, scala, 20).value, 20.minutes)
//Await.result(analyser.distanceMatrix(List("Scala (programming language)","Python (programming language)","Assembly language","Java virtual machine", "Programming language")).value, 20.minutes)
//Await.result(analyser.distanceMatrix(List("École Polytechnique Fédérale de Lausanne", "Scala (programming language)"), 20).value, 20.minutes)
//Await.result(analyser.breadthFirstSearch(python, scala, 5).value, 20.minutes)
//Await.result(wiki.searchId("Scala").value, 2.days)

def combinations[A](l: List[A]): List[(A, A)] = l match  
  case Nil => List.empty[(A, A)]
  case first :: rest => rest.map(first -> _) ++ combinations(rest)

combinations((1 to 4).toList)

def combinations2[A](l: List[A]): List[(A, A)] = l.flatMap(a => l.map(aa => (a, aa)))

combinations2((1 to 2).toList)
