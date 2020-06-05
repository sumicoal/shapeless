package example

import model.{Employee, IceCream}

object file1 extends App {
  import shapeless._

  // 1-1
  val genericEmployee = Generic[Employee].to(Employee("Dave", 123, false))
  // genericEmployee: String :: Int :: Boolean :: shapeless.HNil = Dave :: 123 :: false :: HNil
  println(genericEmployee)

  val genericIceCream = Generic[IceCream].to(IceCream("Sundae", 1, false))
  // genericIceCream: String :: Int :: Boolean :: shapeless.HNil = Sundae :: 1 :: false :: HNil
  println(genericIceCream)

  def genericCsv(gen: String :: Int :: Boolean :: HNil): List[String] =
    List(gen(0), gen(1).toString, gen(2).toString)

  println(genericCsv(genericEmployee))
  // res2: List[String] = List(Dave, 123, false)

  println(genericCsv(genericIceCream))
  // res3: List[String] = List(Sundae, 1, false)


  // 1-3
  
}
