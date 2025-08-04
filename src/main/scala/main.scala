import cats.Functor



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
    import models.{Box}
    import Factory.FunctorSyntax4.boxFunctor
    import cats.syntax.functor.*

    val box = Box[Int](123)
    val res2 = box.map(x => x + 1)
    println(res2)
  }
  // Example C: Tree transformer
  {
    import models.Tree

    import Factory.FunctorSyntaxForTree._
    import cats.syntax.functor.*

    //val tree = Branch(Leaf(10), Leaf(50))
    //val newTree = tree.map(_ * 10)
    val t = Tree.leaf(100).map(_ * 2)
    println(t)
    // TODO: still to fix
    //Tree.branch( Tree.leaf(10), Tree.leaf(20) ).
    //Branch(Leaf(10), Leaf(50))

  }

  // Example D: imap / Codec's
  {
    import Factory.Variance.{stingCodec, doubleCodec, _}
    println( doubleCodec.encode(112.4) )

    import models.{Box}

    println(  boxCodec[Box[Double]].decode("-123.4") )
  }

}
//  (1 to 5).map(println)
//
//  for (i <- 1 to 5) do
//    println(s"i = $i")

