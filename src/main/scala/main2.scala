//import cats.effect.{Async, ExitCode, IO, IOApp, Sync}
//import cats.implicits.catsSyntaxFlatMapOps
//
//import java.util.concurrent.{Executors, TimeUnit}
//import scala.concurrent.Future
//
//object  main2 extends IOApp {
//
//  import cats.effect.IO
//
//  override def run(args: List[String]): IO[ExitCode] = {
//    val iob = (x: Either[Throwable, String]) => IO {
//      println(s"Hay: $x")
//    }
//
//    //val scheduler = Executors.newScheduledThreadPool(1)
//    import scala.concurrent.ExecutionContext.Implicits.global
//
//    def apiCall(): Future[Either[Throwable, String]] = Future{
//      Thread.sleep(3000)
//      println("Data test")
//      Right("Outcome Ok")
//    }
//
////    val ioa: IO[String] = Async[IO].async { cb =>
////      import scala.util.{Failure, Success}
////
////      apiCall()
////        .onComplete {
////          case Success(value) => cb(value)
////          case Failure(error) => cb(Left(error))
////      }
////    }
//
////    val ioAA = ioa.map(s => s"$s => GoGoGo")
////
////    val ioa2 = Sync[IO].delay( {
////      println("Delayed action")
////      throw new Error("Some error")
////    } )
//
//    import cats.effect.Ref
//    val stateModification =  for {
//      ref <- Ref.of[IO, Int](0)
//      _ <- IO.delay( println(s"State: ${ref.get}"))
//      c2  <- ref.modify(x => (x + 1, x))
//    } yield ()
//
//    val neverTerminatedTask = IO.never *> stateModification
//
//    def fib(n: Int, a: Long, b: Long): IO[Long] =
//      IO.defer {
//        if (n > 0)
//          fib(n - 1, b, a + b)
//        else
//          IO.pure(a)
//      }
//
////    import cats.syntax.either._
//    val program: IO[ExitCode] =
//      for {
//        //_ <- iob(Right("String"))
//        //_ <- ioa
//        //_ <- ioa2.attempt
//        //_ <- ioAA // Expect second call with changes string outcome
//        //_ <- iob(Left(new Error("Data")))
//        //_ <- neverTerminatedTask
//        x <- fib(10, 1, 1)
//        _ <- IO.delay( println(s"Result: $x"))
//      } yield ExitCode.Success
//
//    IO.pure {
//      program.unsafeRunSync()
//    }
//  }
//}
