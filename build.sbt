lazy val commonSettings = Seq(
  scalaVersion := "3.0.0",
  scalacOptions ++= Seq("-deprecation"),
  testFrameworks += new TestFramework("munit.Framework"),
  libraryDependencies ++= Seq(
    ("org.creativescala" %% "doodle"           % "0.9.21").cross(CrossVersion.for3Use2_13),
    "org.scalameta"      %% "munit"            % "0.7.26"  % Test,
    "org.scalacheck"     %% "scalacheck"       % "1.15.4"  % Test
  )
)

lazy val courseraSettings = Seq(
  course := "effective-scala",
  assignmentVersion := "076cc74a",
  courseraId := ch.epfl.lamp.CourseraId(
    courseId = "nreZLpQjEeqcUw5ApMKwQQ",
    key = "G72Sx90ZRKer8YEN4UB0YQ",
    itemId = "jiQL9",
    premiumItemId = Some("MT64m"),
    partId = "KnKg4"
  )
)

lazy val week0 = (project in file("week0"))
  .settings(commonSettings)

lazy val week1 = (project in file("week1"))
  .settings(commonSettings)

lazy val week1Fireworks = (project in file("week1-fireworks"))
  .settings(commonSettings)
  .settings(courseraSettings)
  .settings(
    assignment := "fireworks"
  )
  .enablePlugins(StudentTasks)

lazy val root = (project in file("."))
  .aggregate(
    week0,
    week1,
    week1Fireworks
  )
