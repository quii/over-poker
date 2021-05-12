name := "over-poker"

scalaVersion := "2.13.5"

val http4sVersion = "0.21.21"

resolvers += Resolver.url("typesafe", url("https://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)

libraryDependencies += "org.http4s" %% "http4s-dsl" % http4sVersion

libraryDependencies += "org.http4s" %% "http4s-blaze-server" % http4sVersion

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.30"

libraryDependencies += "org.typelevel" %% "cats-effect" % "2.4.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7" % "test"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % "0.13.0",
  "io.circe" %% "circe-literal" % "0.13.0"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")

enablePlugins(JavaAppPackaging)
