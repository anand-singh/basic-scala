import sbt._

object Dependencies {
  lazy val cats      = "org.typelevel"              %% "cats-core"       % "2.8.0"
  lazy val chimney   = "io.scalaland"               %% "chimney"         % "0.6.2"
  lazy val logback   = "ch.qos.logback"              % "logback-classic" % "1.2.11"
  lazy val logging   = "com.typesafe.scala-logging" %% "scala-logging"   % "3.9.5"
  lazy val scalaTest = "org.scalatest"              %% "scalatest"       % "3.2.12" % Test
}
