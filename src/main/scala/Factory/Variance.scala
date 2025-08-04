package Factory

object Variance {
  trait Codec[A] {
    def encode(value: A): String
    def decode(value: String): A
    def imap[B](dec: A => B, enc: B => A) : Codec[B] = {
      val self = this
      new Codec[B] {
        override def encode(value: B): String = self.encode( enc(value) )
        override def decode(value: String): B = dec( self.decode(value) )
      }
    }
  }

  implicit val stingCodec: Codec[String] = new Codec[String] {
    override def encode(value: String): String = value
    override def decode(value: String): String = value
    //override def imap[B](dec: String => B, enc: B => String): Codec[B] = super.imap(dec, enc)
  }
  implicit val doubleCodec: Codec[Double] = stingCodec.imap[Double](_.toDouble, _.toString)

  import models.{Box}

  implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] = c.imap[Box[A]]( Box(_), _.value)

}
