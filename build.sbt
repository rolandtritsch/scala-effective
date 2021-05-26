lazy val commonSettings = Seq(
  scalaVersion := "3.0.0",
  scalacOptions ++= Seq(
    "-deprecation",
    "-language:implicitConversions",
    "-Xfatal-warnings"
  ),
  onChangedBuildSource := ReloadOnSourceChanges,
  testFrameworks += new TestFramework("munit.Framework"),
  libraryDependencies ++= Seq(
    ("org.creativescala" %% "doodle" % "0.9.21")
      .cross(CrossVersion.for3Use2_13),
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.novocode" % "junit-interface" % "0.11" % Test,
    "org.scalacheck" %% "scalacheck" % "1.15.4" % Test,
    "org.scalameta" %% "munit" % "0.7.26" % Test
  )
)

lazy val courseraSettings = Seq(
  course := "effective-scala",
  assignmentVersion := "076cc74a"
)

lazy val week0 = (project in file("week0"))
  .settings(commonSettings)

lazy val week1 = (project in file("week1"))
  .settings(commonSettings)

lazy val week1Fireworks = (project in file("week1-fireworks"))
  .enablePlugins(StudentTasks)
  .settings(commonSettings)
  .settings(courseraSettings)
  .settings(
    assignment := "fireworks",
    courseraId := ch.epfl.lamp.CourseraId(
      courseId = "nreZLpQjEeqcUw5ApMKwQQ",
      key = "G72Sx90ZRKer8YEN4UB0YQ",
      itemId = "jiQL9",
      premiumItemId = Some("MT64m"),
      partId = "KnKg4"
    )
  )

lazy val week2 = (project in file("week2"))
  .settings(commonSettings)

lazy val week2Democracy = (project in file("week2-democracy"))
  .enablePlugins(StudentTasks)
  .settings(commonSettings)
  .settings(courseraSettings)
  .settings(
    assignment := "democracy",
    courseraId := ch.epfl.lamp.CourseraId(
      courseId = "nreZLpQjEeqcUw5ApMKwQQ",
      key = "-6HatvbuQ6eEaWZIZkGxMQ",
      itemId = "vujw6",
      premiumItemId = Some("Y3zSS"),
      partId = "1Aqma"
    )
  )

lazy val week3Todo = (project in file("week3-todo"))
  .enablePlugins(StudentTasks)
  .settings(commonSettings)
  .settings(courseraSettings)
  .settings(
    assignment := "todo",
    assignmentVersion.withRank(KeyRanks.Invisible) := "c54d05f7",
    courseraId := ch.epfl.lamp.CourseraId(
      courseId = "nreZLpQjEeqcUw5ApMKwQQ",
      key = "DMKicF0vRSeW7NaQkYj92w",
      itemId = "UTVcC",
      premiumItemId = Some("adwoD"),
      partId = "uZst0"
    ),
    libraryDependencies ++= Seq(
      ("io.circe" %% "circe-parser" % "0.13.0")
        .cross(CrossVersion.for3Use2_13),
      ("org.http4s" %% "http4s-ember-server" % "1.0.0-M4")
        .cross(CrossVersion.for3Use2_13),
      ("org.http4s" %% "http4s-circe" % "1.0.0-M4")
        .cross(CrossVersion.for3Use2_13),
      ("org.http4s" %% "http4s-dsl" % "1.0.0-M4")
        .cross(CrossVersion.for3Use2_13)
    )
  )

lazy val root = (project in file("."))
  .aggregate(
    week0,
    week1,
    week1Fireworks,
    week2,
    week2Democracy,
    week3Todo
  )
