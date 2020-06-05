package example

import shapeless._

object coproduct extends App {

  case class Red()
  case class Amber()
  case class Green()

  type Light = Red :+: Amber :+: Green :+: CNil

  val red: Light = Inl(Red())

  val green: Light = Inr(Inr(Inl(Green())))

  println(red)
  println(green)

  val a = Tuple2(1, 3)
  val b = Tuple2("aa", "bb")

}

object Polytest extends App {

  trait Case[P, A] {
    type Result

    def apply(a: A): Result
  }

  trait Poly {
    def apply[A](arg: A)(implicit cse: Case[this.type, A]): cse.Result =
      cse.apply(arg)
  }

  object myPoly extends Poly {

    implicit def intCase =
    // trat の new は 無名クラスを作る
      new Case[this.type, Int] {
        type Result = Double

        def apply(num: Int): Double = num / 2.0
      }

    implicit def stringCase =
      new Case[this.type, String] {
        type Result = Int

        def apply(str: String): Int = str.length
      }
  }

  object youPoly extends Poly1 {
    implicit val intCase: Case.Aux[Int, Double] =
      at(num => num / 2.0)

    implicit val stringCase: Case.Aux[String, Int] =
      at(str => str.length)
  }

  println(myPoly.apply(123))
  println(myPoly.apply("sdfsadf"))
  println(youPoly.apply(123))
  println(youPoly.apply("sdfsadf"))

  // 型推論でエラー
  //  val a = youPoly.apply(123)
  //  val b: Double = a
  //
  //  val c: Double = youPoly.apply(123)

}
object Polymap extends App {
  object sizeOf extends Poly1 {
    implicit val intCase: Case.Aux[Int, Int] =
      at(identity)

    implicit val stringCase: Case.Aux[String, Int] =
      at(_.length)

    implicit val booleanCase: Case.Aux[Boolean, Int] =
      at(bool => if(bool) 1 else 0)
  }
  println((10 :: "hello" :: true :: HNil).map(sizeOf))
}

object PolyClass extends App {
  trait ProductMapper[A, B, P] {
    def apply(a: A): B
  }

  import shapeless._
  import shapeless.ops.hlist

  implicit def genericProductMapper[
    A, B,
    P <: Poly,
    ARepr <: HList,
    BRepr <: HList
  ](
    implicit
    aGen: Generic.Aux[A, ARepr],
    bGen: Generic.Aux[B, BRepr],
    mapper: hlist.Mapper.Aux[P, ARepr, BRepr]
  ): ProductMapper[A, B, P] =
    new ProductMapper[A, B, P] {
      def apply(a: A): B =
        bGen.from(mapper.apply(aGen.to(a)))
      //
    }

  // aux(インスタンス, インスタンスのunapply:HList)

  implicit class ProductMapperOps[A](a: A) {
    class Builder[B] {
      def apply[P <: Poly](poly: P)
        (implicit pm: ProductMapper[A, B, P]): B =
        pm.apply(a)
    }

    def mapTo[B]: Builder[B] = new Builder[B]
  }

  object conversions extends Poly1 {
    implicit val intCase:  Case.Aux[Int, Boolean]   = at(_ > 0)
    implicit val boolCase: Case.Aux[Boolean, Int]   = at(if(_) 1 else 0)
    implicit val strCase:  Case.Aux[String, String] = at(identity)
  }

  case class IceCream1(name: String, numCherries: Int, inCone: Boolean)
  case class IceCream2(name: String, hasCherries: Boolean, numCones: Int)


  //A: IceCream1, B: IceCream2, C: P conversions poly1をもっているもの
  IceCream1("Sundae", 1, false).mapTo[IceCream2](conversions)
  // res2: IceCream2 = IceCream2(Sundae,true,0)
  println(IceCream1("Sundae", 1, false).mapTo[IceCream2](conversions))
}
