package wikigraph

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import Articles.ArticleId

/** Analyze the graph of Wikipedia Articles
  *
  * @param client
  *   the wikipedia client providing access to the data.
  */
final class Wikigraph(client: Wikipedia):

  /** Retrieves the names of the articles linked in a page.
    *
    * @param of
    *   the id of the page from which the links are retrieved
    *
    * Hint: Use the methods that you implemented in WikiResult.
    */
  def namedLinks(of: ArticleId): WikiResult[Seq[String]] =
    client.linksFrom(of).flatMap { ids =>
      WikiResult.traverse[ArticleId, String](ids.toSeq) { id =>
        client.nameOfArticle(id)
      }
    }

  /** Computes the distance between two articles using breadth first search.
    *
    * @param start
    *   compute the distance from this node to `target`
    * @param target
    *   compute the distance from `start` to this node
    * @param maxDepth
    *   stop if the depth exceeds this value
    *
    * @return
    *   an asynchronous computation that might fail. If the maximal distance is
    *   exceeded during the search, the result is None
    *
    * Note: if a domain error occurs when jumping from node to node, fallback by
    * ignoring the problematic node
    *
    * Hint: More information is provided in the description of the assignment
    * Use the enqueue and dequeue methods of Queue.
    */
  def breadthFirstSearch(
      start: ArticleId,
      target: ArticleId,
      maxDepth: Int
  ): WikiResult[Option[Int]] =
    import scala.collection.immutable.Queue

    /** This recursive method iterates on the graph.
      *
      * The algorithm is detailed in the assignment description.
      *   - When the queue is empty or the maxDepth is exceeded (in the next
      *     element of the queue), the search fails with None
      *   - Otherwise a node is retrieved from the queue and its neighbors
      *     fetched from the dataset. The search succeeds if `target` is in this
      *     set. Otherwise we recursively search after modifying `unknowns` and
      *     adding the unknown neighbors to the queue with the correct distance.
      *
      * @param visited
      *   keep the nodes the are already visited, used no to iterate infinitely
      *   on graph cycles
      * @param q
      *   the next nodes to visit and their distance from `start`
      */
    def iter(
        visited: Set[ArticleId],
        q: Queue[(Int, ArticleId)]
    ): WikiResult[Option[Int]] =
      if q.isEmpty then WikiResult.successful(None)
      else
        val current @ ((depth, articleId), qNext) = q.dequeue
        if depth > maxDepth then WikiResult.successful(None)
        else
          client.linksFrom(articleId)
            .fallbackTo(WikiResult.successful(Set.empty[ArticleId]))
            .flatMap { neighbors =>
              if neighbors.contains(target) then
                WikiResult.successful(Some(depth))
              else
                val notVisitedYet = neighbors.diff(visited)
                iter(
                  visited + articleId,
                  qNext.enqueueAll(
                    notVisitedYet.map(depth + 1 -> _)
                  )
                )
            }

    if start == target then WikiResult.successful(Some(0))
    else iter(Set(start), Queue(1 -> start))

  /** Computes the distances between some pages provided the list of their
    * titles. Do not compute the distance from page and itself.
    *
    * @param titles
    *   names of the articles
    * @param maxDepth
    *   stop the search this value of distance is exceeded
    *
    * @return
    *   An asynchronous computation of the following form:
    *   Seq((distanceFromTitle, distanceToTitle, distance), ...)
    *
    * Hint: You should use the methods that you implemented on WikiResult as
    * well as breadthFirstSearch
    */
  def distanceMatrix(
      titles: List[String],
      maxDepth: Int = 50
  ): WikiResult[Seq[(String, String, Option[Int])]] =
    def combinations[A](l: Seq[A]): Seq[(A, A)] =
      for {
        a <- l
        aa <- l
        if a != aa
      } yield (a, aa)

    val titlesResults = WikiResult.successful[Seq[String]](titles.toSeq)
    val titlesPairsResults = titlesResults.flatMap {
      ts => WikiResult.successful(combinations(ts))
    }
    val idsResults = titlesResults.flatMap {
      ts => WikiResult.traverse(ts)(client.searchId)
    }
    val idsPairsResults = idsResults.flatMap {
      ids => WikiResult.successful(combinations(ids))
    }
    val distanceResults = idsPairsResults.flatMap {
      pairs => WikiResult.traverse(pairs) {
        (start, target) => breadthFirstSearch(start, target, maxDepth)
      }
    }
    val zippedResults = titlesPairsResults.zip(distanceResults)
    zippedResults.flatMap {
      (titles, distances) =>
        val zipped = titles.zip(distances).map {
          (t, d) => (t._1, t._2, d) 
        }
        WikiResult.successful(zipped)
    }
      
end Wikigraph
