package week1

import munit.*

class HouseSuite extends FunSuite {

  test("house") {
    assert(house(facade(5,5), door(1,2), 2 * window(1,1)) == 21)
  }

}
