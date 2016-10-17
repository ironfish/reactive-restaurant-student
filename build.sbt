val akkaVer = "2.4.11"
val logbackVer = "1.1.7"
val scalaVer = "2.11.8"
val scalaParsersVer= "1.0.4"
val scalaTestVer = "3.0.0"

lazy val compileOptions = Seq(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

lazy val commonDependencies = Seq(
  "com.typesafe.akka"        %% "akka-actor"                 % akkaVer,
  "com.typesafe.akka"        %% "akka-slf4j"                 % akkaVer,
  "ch.qos.logback"           %  "logback-classic"            % logbackVer,
  "org.scala-lang.modules"   %% "scala-parser-combinators"   % scalaParsersVer,
  "com.typesafe.akka"        %% "akka-testkit"               % akkaVer            % "test",
  "org.scalatest"            %% "scalatest"                  % scalaTestVer       % "test"
)

lazy val commonSettings = Seq(
  organization := "com.typesafe.training",
  version := "1.0.0",
  scalaVersion := scalaVer,
  scalacOptions ++= compileOptions,
  unmanagedSourceDirectories in Compile := List((scalaSource in Compile).value),
  unmanagedSourceDirectories in Test := List((scalaSource in Test).value),
  EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
  EclipseKeys.eclipseOutput := Some(".target"),
  EclipseKeys.withSource := true,
  EclipseKeys.skipParents in ThisBuild := true,
  EclipseKeys.skipProject := true,
  parallelExecution in Test := false,
  logBuffered in Test := false,
  parallelExecution in ThisBuild := false,
  libraryDependencies ++= commonDependencies
)

lazy val base = (project in file("."))
  .aggregate(
    common,
    entry_state,
    exercise_001_implement_actor,
    exercise_002_top_level_actor,
    exercise_003_message_actor,
    exercise_004_use_sender,
    exercise_005_create_child_actors,
    exercise_006_actor_state,
    exercise_007_use_scheduler,
    exercise_008_keep_actor_busy,
    exercise_009_stop_actor,
    exercise_010_lifecycle_monitoring,
    exercise_011_faulty_actor,
    exercise_012_custom_supervision,
    exercise_013_another_faulty_actor,
    exercise_014_self_healing,
    exercise_015_detect_bottleneck,
    exercise_016_use_router,
    exercise_017_config_dispatcher,
    exercise_018_become_stash)
  .settings(commonSettings: _*)

lazy val common = project.settings(commonSettings: _*)

lazy val entry_state = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_001_implement_actor = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_002_top_level_actor = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_003_message_actor = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_004_use_sender = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_005_create_child_actors = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_006_actor_state = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_007_use_scheduler = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_008_keep_actor_busy = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_009_stop_actor = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_010_lifecycle_monitoring = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_011_faulty_actor = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_012_custom_supervision = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_013_another_faulty_actor = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_014_self_healing = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_015_detect_bottleneck = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_016_use_router = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_017_config_dispatcher = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_018_become_stash = project
  .settings(commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
