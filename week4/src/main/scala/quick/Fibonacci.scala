package quick

def fibonacci(n: Int): Int =
  require(n >= 0, "n >= 0")
  n match
    case 0 => 0
    case 1 => 1
    case _ => fibonacci(n - 1) + fibonacci(n - 2)

def fibonacci2(n: Int): Int =
  require(n >= 0, "n >= 0")
  val (result, _) = (1 to n).foldLeft(0,1) {
    (f, _) => (f(1), f(1) + f(0))
  }
  result 

import scala.annotation.tailrec
def fibonacci3(n: Int): Int =
  require(n >= 0, "n >= 0")
  @tailrec
  def fib(f: (Int, Int), n: Int): Int = n match
    case 0 => f(0)
    case _ => fib((f(1), f(1) + f(0)), n - 1)
  fib((0,1), n)

import scala.collection.immutable
val fibonacci4: immutable.LazyList[Int] = 
  0 #:: 1 #:: fibonacci4.zip(fibonacci4.tail).map{ n => n(0) + n(1) }

@main def main(n: Int): Unit =
  println(s"fibonacci(${n}): ${fibonacci(n)}")
  println(s"fibonacci2(${n}): ${fibonacci2(n)}")
  println(s"fibonacci3(${n}): ${fibonacci3(n)}")
  println(s"fibonacci4(${n}): ${fibonacci4(n)}")
