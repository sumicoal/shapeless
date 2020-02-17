package example

import model._
import shapeless._

object Hello extends Greeting with App {
  println(greeting)
  val a = HList("a", "b")
  println(a)

  //1-1
  val genericEmployee = Generic[Employee].to(Employee("Dave", 123, false))
  // genericEmployee: String :: Int :: Boolean :: shapeless.HNil = Dave :: 123 :: false :: HNil

  val genericIceCream = Generic[IceCream].to(IceCream("Sundae", 1, false))
  // genericIceCream: String :: Int :: Boolean :: shapeless.HNil = Sundae :: 1 :: false :: HNil

  def genericCsv(gen: String :: Int :: Boolean :: HNil): List[String] =
    List(gen(0), gen(1).toString, gen(2).toString)

  val gEmp = genericCsv(genericEmployee)

  // res2: List[String] = List(Dave, 123, false)

  val gIce = genericCsv(genericIceCream)

  println(gEmp)
  println(gIce)
  // res3: List[String] = List(Sundae, 1, false)

  val product: String :: Int :: Boolean :: HNil =
    "Sunday" :: 1 :: false :: HNil

  val b = (1, 2, 3, 4)

  println(product)

  val newProduct = 42L :: product
  println(newProduct)

  val iceCreamGen = Generic[IceCream]
  val iceCream = IceCream("Sundae", 1, false)

  val repr = iceCreamGen.to(iceCream)

  val iceCream2 = iceCreamGen.from(repr)

}

trait Greeting {
  lazy val greeting: String = "hello"
}
