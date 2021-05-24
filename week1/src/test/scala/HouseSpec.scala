package week1

import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class HouseSpec extends AnyFlatSpec with should.Matchers {

  "house" should "the right result" in {
    house(facade(5,5), door(1,2), 2 * window(1,1)) shouldEqual 21
  }

}
