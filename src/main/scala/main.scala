import cats.Functor
import models.Box


@main
def main(): Unit = {
  // Example A:
  {
    import Factory.FunctorSyntax.doMath
    val res: Option[Int] = doMath( Option(20))
    println(res)
  }

  // Example B:
  {
    import Factory.FunctorSyntax4.boxFunctor
    import cats.syntax.functor.*

    val box = Box[Int](123)
    val res2 = box.map(x => x + 1)
    println(res2)
  }

}
//  (1 to 5).map(println)
//
//  for (i <- 1 to 5) do
//    println(s"i = $i")

