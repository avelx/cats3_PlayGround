import cats.effect.kernel.Deferred
import cats.effect.{IO, IOApp}
import cats.effect.{IO, Deferred}
import cats.syntax.all._

object mainDeffer extends IOApp.Simple {

  def start(d: Deferred[IO, Int]): IO[Unit] = {
    val attemptCompletion: Int => IO[Unit] = n => d.complete(n).void

    List(
      IO
        .race(attemptCompletion(1), attemptCompletion(2)),
      IO
        .race(attemptCompletion(3), attemptCompletion(4))
      ,
      d.get.flatMap { n => IO(println(show"Result: $n")) }
    ).parSequence.void
  }

  // Refs: https://typelevel.org/cats-effect/docs/std/deferred
  val program: IO[Unit] =
    for {
      syncPrimitive <- Deferred[IO, Int]
      _ <- start(syncPrimitive)
    } yield ()

  override def run: IO[Unit] = {
    program
  }

}
