package example

import model.{Circle, Rectangle, Shape}

object file2 extends App {
  import shapeless._

  /*
  * ジェネリック・プログラミングの主な考え方は、
  * 少量のジェネリック・コードを書くことで、
  * 多種多様な型の問題を解決することです。
  * Shapelessはこの目的のために2つのツールを提供しています。
  */

  // 2
  val rect: Shape = Rectangle(3.0, 4.0)
  println(rect)

  val circ: Shape = Circle(1.0)
  println

  def area(shape: Shape): Double =
    shape match {
      case Rectangle(w, h) => w * h
      case Circle(r)       => math.Pi * r * r
    }

  area(rect)
  // res1: Double = 12.0

  area(circ)
  // res2: Double = 3.141592653589793


}
