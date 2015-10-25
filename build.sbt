name := "over-poker"

scalaVersion := "2.11.7"

resolvers += Resolver.url("typesafe", url("http://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

libraryDependencies += "org.http4s" %% "http4s-dsl"          % "0.10.1"

libraryDependencies += "org.http4s" %% "http4s-blaze-server" % "0.10.1"

libraryDependencies += "org.http4s" %% "http4s-argonaut" % "0.10.1"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.6.4"

libraryDependencies += "org.typelevel" %% "scalaz-scalatest" % "0.3.0" % "test"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")

enablePlugins(JavaServerAppPackaging)

enablePlugins(DockerPlugin)

dockerExposedPorts := Seq(8080)


