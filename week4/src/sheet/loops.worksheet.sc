
def fibonacci(n: Int): Int =
  require(n >= 0, "n >= 0")
  n match
    case 0 => 0
    case 1 => 1
    case _ => fibonacci(n - 1) + fibonacci(n - 2)

fibonacci(0)
fibonacci(1)
fibonacci(5)
fibonacci(42)

def fibonacci2(n: Int): Int =
  require(n >= 0, "n >= 0")
  val (result, _) = (1 to n).foldLeft(0,1) {
    (f, _) => (f(1), f(1) + f(0))
  }
  result 

fibonacci2(0)
fibonacci2(1)
fibonacci2(5)

import scala.annotation.tailrec
def fibonacci3(n: Int): Int =
  require(n >= 0, "n >= 0")
  @tailrec
  def fib(f: (Int, Int), n: Int): Int = n match
    case 0 => f(0)
    case _ => fib((f(1), f(1) + f(0)), n - 1)
  fib((0,1), n)

fibonacci3(0)
fibonacci3(1)
fibonacci3(5)
