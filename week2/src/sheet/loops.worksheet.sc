
def factorial(n: Int): Int =
  require(n >= 0, "n >= 0")
  n match
    case 0 => 1
    case _ => factorial(n - 1) * n

factorial(0)
factorial(1)
factorial(5)

def factorial2(n: Int): Int =
  require(n >= 0, "n >= 0")
  (1 to n).foldLeft(1)((f, x) => f * x)

factorial2(0)
factorial2(1)
factorial2(5)

import scala.annotation.tailrec
def factorial3(n: Int): Int =
  require(n >= 0, "n >= 0")
  @tailrec
  def f(x: Int, a: Int): Int = x match
    case 0 => a
    case _ => f(x - 1, a * x)
  f(n, 1)

factorial3(0)
factorial3(1)
factorial3(5)
