ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "cats3_PlayGround"
  )


libraryDependencies += "org.typelevel" %% "cats-core" % "2.13.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.6.2"

lazy val doobieVersion = "1.0.0-RC10"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion
)

libraryDependencies ++= Seq("org.typelevel" %% "shapeless3-deriving" % "3.4.0")
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.11.3"