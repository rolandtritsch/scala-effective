package week1

@main def main(): Unit = {
  println(s"House: ${house(facade(5,5), door(1,2), 2 * window(1,1))}")
}

def house(facade: Double, door: Double, window: Double): Double = {
  facade - door - window
}

def facade(l: Double, h: Double) = l * h
def door(l: Double, h: Double) = l * h
def window(l: Double, h: Double) = l * h
