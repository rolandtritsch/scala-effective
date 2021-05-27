package quick

import org.scalacheck.Prop.forAll
import org.scalacheck.Gen

val fibonacciDomain = Gen.choose[Int](1, 42)

class FibonacciProperties extends munit.ScalaCheckSuite:
  property("fibonacci(n) == fibonacci(n - 2) + fibonacci(n - 1)") {
    forAll (fibonacciDomain.suchThat(n => n >= 3)) { (n: Int) => {
        fibonacci(n) == fibonacci(n - 2) + fibonacci(n - 1)
    }}
  }

  property("fibonacci(n) >= 0") {
    forAll (fibonacciDomain) { (n: Int) => {
        fibonacci(n) >= 0
    }}
  }

  property("fibonacci(n) same for all implementations") {
    forAll (fibonacciDomain) { (n: Int) => {
        val f = fibonacci(n)
        f == fibonacci2(n)
        && f == fibonacci3(n)
        && f == fibonacci4(n)
    }}
  }
