package Factory




object Monoids {

  import cats.Monoid
  import cats.instances.string._ // for Monoid
  import cats.syntax.invariant._ // for imap
  implicit val symbolMonoid : Monoid[Symbol] = Monoid[String].imap(Symbol.apply)(_.name)

}
