import cats.effect.{Async, ExitCode, IO, IOApp}
import cats.implicits.catsSyntaxFlatMapOps

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.Future

object  main2 extends IOApp {

  import cats.effect.IO

  override def run(args: List[String]): IO[ExitCode] = {
    val iob = (x: Either[Throwable, String]) => IO {
      println(s"Hay: $x")
    }

    //val scheduler = Executors.newScheduledThreadPool(1)
    import scala.concurrent.ExecutionContext.Implicits.global

    val apiCall = Future{
      Thread.sleep(3000)
      println("Data test")
      "Outcome Ok"
    }

    val ioa: IO[String] = Async[IO].async { cb =>
      import scala.util.{Failure, Success}

      apiCall.onComplete {
        case Success(value) => cb(Right(value))
        case Failure(error) => cb(Left(error))
      }
    }


//    import cats.syntax.either._
    val program: IO[ExitCode] =
      for {
        _ <- iob(Right("String"))
        x <- ioa
        _ <- iob(Left(new Error("Data")))
      } yield ExitCode.Success

    IO.pure {
      program.unsafeRunSync()
    }
  }
}
