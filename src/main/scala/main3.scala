import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits.catsSyntaxApplicativeError

import scala.concurrent.Future

object  main3 extends IOApp {

  trait Dummy[F[_]] {
    def runSomething[A, B](): Unit = {

      val first: F[A] = ???
      val second: F[B] = ???

      //val third: F[_] = first *> second // Need IO to be in scope
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    //val sideEffectCall = IO.pure(throw new Error("SomeError")) // Don't !!!

    val errorAttempt: IO[Int] = IO
      .raiseError(new RuntimeException("Some Error"))
      .handleError(_ => 12) // Error Handling or use handleErrorWith

    val errorAttemptTwo: IO[Int] = IO
      .raiseError(new RuntimeException("Some Error"))
      .adaptErr(t => new Exception(t)) // Error transformation

    // Lifting the error
    val errorAttemptTwoLifted: IO[Either[Throwable, Int]] = errorAttemptTwo.attempt

    val errorEffect = IO(throw new Error("On no ..."))

    val fromFuture : IO[String] = IO.fromFuture( IO( Future.successful("Some value")) ) // Internal IO wrapper would delay Future execution

    val futureNew = fromFuture.map(x => s"This would be a new string: $x") // Strange ... this would be produce nothing !!!

//    futureNew.onError{
//      case x: Throwable => IO.unit
//    }
    //futureNew.recover

    IO.delay(println("Here is our test ...")) *> errorAttempt *>
      fromFuture *> errorAttemptTwoLifted  *> IO.pure(ExitCode.Success)// *> errorEffect //*> sideEffectCall
  }

}
