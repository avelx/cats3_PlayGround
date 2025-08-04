package Factory

import cats.Functor


object FunctorSyntax {
  import cats.syntax.functor.*

  def doMath[F[_]](start: F[Int])
                  (implicit functor: Functor[F]): F[Int] = {
    start.map(n => (n + 1) * 2)
  }
}

object FunctorSyntax2 {

  implicit class FunctorOps[F[_], A](src: F[A]) {
    def map[B](func: A => B)
              (implicit functor: Functor[F]): F[B] = {
        functor.map(src)(func)
    }
  }
}

object FunctorSyntax3 {
  implicit val optionFunctor: Functor[Option] = {
    new Functor[Option] {
      override def map[A, B](value: Option[A])(f: A => B): Option[B] = {
        value.map(f)
      }
    }
  }
}

object FunctorSyntax4 {
  import models.Box
  implicit val boxFunctor: Functor[Box] = {
    new Functor[Box] {
      override def map[A, B](x: Box[A])(f: A => B): Box[B] = {
        Box[B]( f(x.value) )
      }
    }
  }
}

object FunctorSyntaxForTree {
  import models._

  implicit val treeFunctor: Functor[Tree] = {
    new Functor[Tree] {
      override def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = {
        tree match {
          case Branch(left, right) => 
            Branch(map(left)(f), map(right)(f))
          case Leaf(value) => Leaf(f(value))
        }
      }
    }
  }

}