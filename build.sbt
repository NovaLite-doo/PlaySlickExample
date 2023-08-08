name := """play-slick-postgres"""
organization := "rs.novalite"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  guice,
  // Database
  "com.typesafe.play" %% "play-slick" % "5.1.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.1.0",
  "org.postgresql" % "postgresql" % "42.5.0",
  // Security
  "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0",
  // Swagger
  "org.webjars" % "swagger-ui" % "4.15.0",
  // Test
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "rs.novalite.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "rs.novalite.binders._"

// Swagger
swaggerDomainNameSpaces := Seq("models", "dtos")
