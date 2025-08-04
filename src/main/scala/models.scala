package object models {
  final case class Box[A](value: A)

  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value : A) extends Tree[A]

  object Tree {
    def branch[A](left: Tree[A], right: Tree[A]) = Branch(left, right)

    //def branchInt[Int](left: Tree[Int], right: Tree[Int]) = Branch(left, right)
    def leaf[A](value : A): Tree[A] = Leaf(value)
  }
}
