package Factory

object Mondas {

  /*
  trait Monad[F[_]] {
    def pure[A](value: A): F[A]
    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]
  }*/
  // Laws:
  //  1: Left identify:   pure(a).flatMap(func) == func(a)
  //  2: Right identity:  m.flatMap(pure) == m
  //  3. Associativity:   m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))

  import cats.Monad
  import cats.syntax.functor._  // for map
  import cats.syntax.flatMap._  // for flatMap
  //Obsolete import: import scala.language.higherKinds
  
  def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
    a.flatMap(x => b.map(y => x * x + y *y))
}
