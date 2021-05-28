package quickcheck

import org.scalacheck.*
import Arbitrary.*
import Gen.*
import Prop.*

class HeapProperties(heapInterface: HeapInterface) extends Properties("Heap"):
  
  // Import all the operations of the `HeapInterface` (e.g., `empty`
  // `insert`, etc.)
  import heapInterface.*

  // Examples of properties
  property("inserting the minimal element and then finding it should return the same minimal element") =
    forAll { (heap: List[Node]) =>
      val min = if isEmpty(heap) then 0 else findMin(heap)
      findMin(insert(min, heap)) == min
    }

  property("the minimum of a heap of two elements should be the smallest of the two elements") =
    forAll { (x1: Int, x2: Int) =>
      val heap = insert(x2, insert(x1, empty))
      val min: Int = Math.min(x1, x2)
      findMin(heap) == min
    }

  property("delete minumum of heap of one element should return an empty heap") =
    forAll { (x: Int) =>
      // create a heap with exactly one element, `x`
      val heap1: List[Node] = insert(x, empty)
      // delete the minimal element from it
      val heap0: List[Node] = deleteMin(heap1)
      // check that heap0 is empty
      isEmpty(heap0)      
    }

  property("continually finding and deleting the minimal element of a heap should alwys delete the smallest element") =
    // recursively traverse the heap
    def check(heap: List[Node]): Boolean =
      // if the heap is empty, or if it has just one element, we have
      // successfully finished our checks
      if isEmpty(heap) || isEmpty(deleteMin(heap)) then
        true
      else
        // find the minimal element
        val x1: Int = findMin(heap)
        // delete the minimal element of `heap`
        val heap2: List[Node] = deleteMin(heap)
        // find the minimal element in `heap2`
        val x2: Int = findMin(heap2)
        // check that the deleted element is smaller than the minimal element
        // of the remaining heap, and that the remaining heap verifies the
        // same property (by recursively calling `check`)
        val checked: Boolean = x1 <= x2 && check(heap2)
        checked
    // check arbitrary heaps
    forAll { (heap: List[Node]) =>
      check(heap)
    }

  // TODO Write more properties here to detect the bugs
  // in bogus BinomialHeap implementations

  property("continually finding and deleting the minimal element of a heap should return a sorted list") =
    // recursively traverse the heap
    def traverse(heap: List[Node]): List[Int] =
      if isEmpty(heap) then List.empty[Int]
      else findMin(heap) +: traverse(deleteMin(heap))
    // check arbitrary heaps
    forAll { (heap: List[Node]) =>
      val traversed = traverse(heap)
      traversed.sorted == traversed
    }
 
  property("continually finding and deleting the minimal element of a heap should return a sorted list (building heap from values)") =
    // recursively traverse the heap
    def traverse(heap: List[Node]): List[Int] =
      if isEmpty(heap) then List.empty[Int]
      else findMin(heap) +: traverse(deleteMin(heap))
    // check arbitrary heaps
    forAll { (values: List[Int]) =>
      val heap = values.foldLeft(empty)((h, v) => insert(v, h))
      val traversed = traverse(heap)
      traversed == values.sorted
    }
 
  property("melding a heap with an empty heap should give you the heap") =
    forAll { (heap: List[Node]) =>
      meld(heap, empty) == heap && meld(empty, heap) == heap
    }

  property("finding a minimum of the melding of any two heaps should return a minimum of one or the other") =
    forAll { (thizHeap: List[Node], thazHeap: List[Node]) =>
      if isEmpty(thizHeap) || isEmpty(thazHeap) then true
      else 
        val min = Math.min(findMin(thizHeap), findMin(thazHeap))
        val meldedHeap = meld(thizHeap, thazHeap)
        findMin(meldedHeap) == min
    }

// random heap generator --- DO NOT MODIFY
  private lazy val genHeap: Gen[List[Node]] = oneOf(const(empty),
    for
      v <- arbitrary[Int]
      h <- oneOf(const(empty), genHeap)
    yield insert(v, h)
  )

  private given Arbitrary[List[Node]] = Arbitrary(genHeap)
  
end HeapProperties
