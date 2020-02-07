package example

import shapeless._

object Hello extends Greeting with App {
  println(greeting)
  val a = HList("a", "b")
  println(a)
}

trait Greeting {
  lazy val greeting: String = "hello"
}
