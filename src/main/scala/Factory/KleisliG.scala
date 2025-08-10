package Factory

import cats.data.Kleisli

object KleisliG {

  private val split: Kleisli[Option, String, String] =
    Kleisli((in: String) => {
      val arr: Array[String] = in.split(",")
      if (arr.length > 0) Some(arr(0)) else None
    })
  private val parse: Kleisli[Option, String, Int] =
    Kleisli((s: String) => if (s.matches("-?[0-9]+")) Some(s.toInt) else None)

  private val reciprocal: Kleisli[Option, Int, Double] =
    Kleisli((i: Int) => if (i != 0) Some(1.0 / i) else None)

  val parseAndReciprocal: Kleisli[Option, String, Double] =
    reciprocal
      .compose(parse)
      .compose(split)
}
