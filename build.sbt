name := "over-poker"

scalaVersion := "2.13.5"

val http4sVersion = "0.21.21"

resolvers += Resolver.url("typesafe", url("https://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.2.8" % "test"

libraryDependencies += "org.http4s" %% "http4s-dsl"          % http4sVersion

libraryDependencies += "org.http4s" %% "http4s-blaze-server" % http4sVersion

libraryDependencies += "org.http4s" %% "http4s-argonaut" % http4sVersion

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.30"

libraryDependencies += "org.typelevel" %% "cats-effect" % "2.4.0"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-circe" % http4sVersion,
  // Optional for auto-derivation of JSON codecs
  "io.circe" %% "circe-generic" % "0.13.0",
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % "0.13.0"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")

enablePlugins(JavaAppPackaging)
