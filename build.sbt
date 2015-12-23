organization := "movio.api"

name := "movio-kafka-lib"

crossScalaVersions := Seq("2.10.6", "2.11.7")

lazy val root = project
  .in( file(".") )
  .aggregate(kafkaLib_0_8)

lazy val kafkaLib_0_8 = project
  .in( file("kafka-lib_0_8") )
  .settings(
    organization:= "movio.api",
    name := "kafka-lib_0_8",
    libraryDependencies ++= Seq(
      "org.apache.kafka" %% "kafka" % "0.8.2.2"
    ),
    scalacOptions += "-feature"
  )


releaseVersionBump := sbtrelease.Version.Bump.Minor
releaseTagName := s"${version.value}”"

publishTo <<= version { (v: String) ⇒
  val repo = "https://artifactory.movio.co/artifactory/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("movio snapshots" at repo + "libs-snapshot-local")
  else
    Some("movio releases" at repo + "libs-release-local")
}
