
import scala.deriving.*

// Scala 3 conversion from Tuple to specific cased class
object Tuples:
  def to[A <: Product](value: A)(
    using mirror: Mirror.ProductOf[A]
  ): mirror.MirroredElemTypes = Tuple.fromProductTyped(value)

  def from[A](value: Product)(
    using
    mirror: Mirror.ProductOf[A],
    ev: value.type <:< mirror.MirroredElemTypes
  ): A = mirror.fromProduct(value)

final case class Country(code: String, name: String)
