import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits.catsSyntaxApplicativeError

import java.time.{LocalDate, LocalDateTime}
import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.Random

object  main3 extends IOApp.Simple {

  trait Dummy[F[_]] {
    def runSomething[A, B](): Unit = {

      val first: F[A] = ???
      val second: F[B] = ???

      //val third: F[_] = first *> second // Need IO to be in scope
    }
  }
  override def run: IO[Unit] = {
    //val sideEffectCall = IO.pure(throw new Error("SomeError")) // Don't !!!

    val id = IO.pure(10)

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

    val simpleAwait = timer.sleep(10.seconds)
    val tr = for {
       trace <- IO.trace
      _ <- trace.printFiberTrace()
    } yield ()



    tickingClock

//    tr *> IO.delay(println("Here is our test ...")) *> errorAttempt *> simpleAwait *>
//      fromFuture *> errorAttemptTwoLifted  *> IO.pure(ExitCode.Success)// *> errorEffect //*> sideEffectCall
  }


  private val tickingClock: IO[Unit] = {
    for {
      id <- IO(UUID.randomUUID())
      _ <- IO.delay(println(s"Id: ${id} Time: - ${LocalDateTime.now()}"))
      _ <- IO.sleep(1.second)
      _ <- tickingClock
    } yield ()
  }

}
