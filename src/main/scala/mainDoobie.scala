import cats.effect.{IO, IOApp}
import doobie.{ConnectionIO, *}
import doobie.implicits.*
import cats.*
import cats.effect.*
import cats.effect.unsafe.implicits.global
import doobie.util.transactor.Transactor.{Aux, interpret}

object  mainDoobie extends IOApp.Simple {

  // Declare connection
  val xa: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://localhost:5432/geo",
    user = "postgres",
    password = "avel_pass",
    logHandler = None
  )

  // Specify read monad
  val readCountry: IO[List[(String, String)]] = sql"select code, name from country"
    .query[(String, String)]
    .to[List]
    .transact(xa)

  private def insertCountry(code: String, name: String, population: Int, gnp: Double) = {
    sql"insert into country (code, name, population, gnp) values ($code, $name, $population, $gnp)"
  }

  val insert: ConnectionIO[Int] = insertCountry("UK", "name", 4500, 4.5).update.run

  override def run: IO[Unit] = {
    for {
      _ <- insert.transact(xa)
      rows <- readCountry
      _ <- IO.delay{
        val countries : List[Country] = rows.map(row => Tuples.from(row))
        countries.foreach(c => println(c))
      }
    } yield ()
  }
}
