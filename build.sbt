import sbt.impl.GroupArtifactID

name := "Dynamic-Scala"

version := "1.0"

scalaVersion := "2.12.2"

val monix =
  "io.monix" %% "monix" ::
  Nil

val benchmark =
  "com.storm-enroute" %% "scalameter" ::
  "com.storm-enroute" %% "scalameter-core" ::
  Nil

val scalactic =
  "org.scalactic" %% "scalactic" ::
  Nil

val scalatest =
  "org.scalatest" %% "scalatest" ::
  Nil

def monixVersion(signature: GroupArtifactID): ModuleID = signature % "2.3.0"
def benchmarkVersion(signature: GroupArtifactID): ModuleID = signature % "0.8.2"
def scalacticVersion(signature: GroupArtifactID): ModuleID = signature % "3.0.1"
def scalatestVersion(signature: GroupArtifactID): ModuleID = signature % "3.0.1" % "test"

libraryDependencies ++=
  monix.map(monixVersion) :::
  benchmark.map(benchmarkVersion) :::
  scalactic.map(scalacticVersion) :::
  scalatest.map(scalatestVersion)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")
addCompilerPlugin("org.spire-math" % "kind-projector" % "0.9.4" cross CrossVersion.binary)

resolvers += Resolver.sonatypeRepo("releases")
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/releases"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
parallelExecution in Test := false

lazy val Benchmark = config("bench") extend Test

lazy val scalaMeterFramework = new TestFramework("org.scalameter.ScalaMeterFramework")

lazy val basic = Project(
  "basic-with-separate-config",
  file("."),
  settings = Defaults.coreDefaultSettings ++ Seq(
    name := "Dynamic-Scala-Benchmark",
    scalaVersion := "2.12.2",
    libraryDependencies ++= Seq(
      "com.storm-enroute" %% "scalameter" % "0.9-SNAPSHOT" % "bench"
    ),
    resolvers ++= Seq(
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
    ),
    testFrameworks in ThisBuild += scalaMeterFramework,
    parallelExecution in Benchmark := false,
    logBuffered := false,
    testOptions in ThisBuild += Tests.Argument(scalaMeterFramework, "-silent")
  )
) configs Benchmark settings {
  inConfig(Benchmark)(Defaults.testSettings): _*
}
