import sbt._

object Dependencies {
  lazy val cats      = "org.typelevel"              %% "cats-core"       % "2.10.0"
  lazy val logback   = "ch.qos.logback"              % "logback-classic" % "1.4.14"
  lazy val chimney   = "io.scalaland"               %% "chimney"         % "0.8.5"
  lazy val logging   = "com.typesafe.scala-logging" %% "scala-logging"   % "3.9.5"
  lazy val scalaTest = "org.scalatest"              %% "scalatest"       % "3.2.17" % Test
}
