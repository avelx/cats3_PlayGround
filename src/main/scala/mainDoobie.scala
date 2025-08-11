import cats.effect.{IO, IOApp}
import doobie.{ConnectionIO, *}
import doobie.implicits.*
import cats.*
import cats.effect.*
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxParallelTraverse1
import doobie.util.transactor.Transactor.{Aux, connect, interpret}

import scala.util.Try

object mainDoobie extends IOApp.Simple {

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

  type CountryTuple = (String, String)
  def insertMany(cs: List[CountryTuple]): ConnectionIO[Int] = {
    val sql = "insert into country (code, name, population, gnp) values (?, ?, 4500, 45.1)"
    Update[CountryTuple](sql).updateMany(cs)
  }
  val path: os.Path = os.root / "Users" / "pavel" / "devcore" / "plaground" / "cats3_PlayGround" / "data" / "countries.csv"

  val readFileLines: IO[List[Country]] = IO.blocking {
    val lines = os.read.lines(path)
    lines
      .map(_.split(","))
      .map(arr => Try(Country(arr(1), arr(0))).toOption)
      .filter(_.isDefined)
      .collect{
        case Some(res) if res.code.length <= 2 => res
      }
      .toList
  }

  override def run: IO[Unit] = {
    for {
      counties <- readFileLines
      _ <- IO.delay( println(counties))
      _ <- insertMany( counties.map(x => (x.code, x.name) )).transact(xa)
      //_ <- insertBatch(counties.toList).flatMap(

      //rows <- readCountry(counties)
      //      _ <- IO.delay{
      //        val countries : List[Country] = rows.map(row => Tuples.from(row))
      //        countries.foreach(c => println(c))
      //      }
    } yield ()
  }
}
