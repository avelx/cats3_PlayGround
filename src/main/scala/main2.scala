import cats.effect.{ExitCode, IO, IOApp}

import java.util.concurrent.{Executors, TimeUnit}

object  main2 extends IOApp {

  import cats.effect.IO
  
  override def run(args: List[String]): IO[ExitCode] = {
    val ioa = IO {
      println("hey!")
    }

    val program: IO[ExitCode] =
      for {
        _ <- ioa
        _ <- ioa
      } yield ExitCode.Success

    IO.pure {
      program.unsafeRunSync()
    }
  }
}
