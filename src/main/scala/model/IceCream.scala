package model

import shapeless.Generic

case class IceCream(
  name:        String,
  numCherries: Int,
  inCone:      Boolean
) {

  val iceCreamGen = Generic[IceCream]

}

object IceCream{
  def iceCreamCsv(c: IceCream): List[String] =
    List(c.name, c.numCherries.toString, c.inCone.toString)
}
