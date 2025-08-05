import cats.{Functor, Monoid}


@main
def main(): Unit = {
  // Example A:
  {
    import Factory.FunctorSyntax.doMath
    val res: Option[Int] = doMath(Option(20))
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
    println(doubleCodec.encode(112.4))

    import models.{Box}

    println(boxCodec[Box[Double]].decode("-123.4"))
  }

  // Example E: Monoids
  {
    import Factory.Monoids._
    import cats.syntax.semigroup._ // for |+|
    import cats.syntax.contravariant._

    val x = Monoid[Symbol].empty
    println(x)

    // Compile error in Scala 3
    //val y = 'a |+| 'few |+| 'words
  }

  // Example F: Unification
  {
    import cats.Functor
    import cats.instances.function._ // for Functor
    import cats.syntax.functor._     // for map

    val func1 = (x: Int) => x.toDouble
    val func2 = (y: Double) => y * 2
    val func3 = func1.map(func2)

    println( func3(101) )
  }

  // Example G: ~?
  {
    import cats.instances.either._
    val either: Either[String, Int] = Left("123")
    println( either.map(_ + 1))

  }

  // Example H: Monads
  {
    import cats.Monad
    import cats.instances.option._
    import cats.instances.list._

    import cats.syntax.applicative._ // for pure
    import Factory.Mondas._

    val opt1 = Monad[Option].pure(3)
    println(opt1)

    val opt2 = 1.pure[Option]
    println(opt2)

    val res = sumSquare(Option(7), Option(3))
    println(res)

    val res2 = sumSquare(List(1, 2), List(3, 4))
    println(res2)
  }

}

