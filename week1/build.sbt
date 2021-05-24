lazy val commonSettings = Seq(
    name := "scala3-week1",
    version := "0.1.0",
    scalaVersion := "3.0.0"
)

lazy val libsTesting = Seq(
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.9",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .settings(libsTesting)
