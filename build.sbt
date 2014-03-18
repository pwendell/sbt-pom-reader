name := "sbt-pom-reader"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.3"

organization := "com.typesafe.sbt"

sbtPlugin := true

publishMavenStyle := false

libraryDependencies ++= Dependencies.pluginDependencies

git.baseVersion := "1.0"

versionWithGit

scriptedLaunchOpts <+= version apply { v => "-Dproject.version="+v }

initialCommands :=
  """| import com.typesafe.sbt.pom._
     | import sbt._
     | val localRepo = file(sys.props("user.home")) / ".m2" / "repository"
     | val pom = loadEffectivePom(localRepo, file("src/sbt-test/simple-pom/can-extract-basics/pom.xml"))
     |""".stripMargin


scriptedSettings

scriptedLaunchOpts <+= version apply { v => "-Dproject.version="+v }

publishTo <<= (version) { v =>
  def scalasbt(repo: String) = ("scalasbt " + repo, "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-" + repo)
  val (name, repo) = if (v.endsWith("-SNAPSHOT")) scalasbt("snapshots") else scalasbt("releases")
  Some(Resolver.url(name, url(repo))(Resolver.ivyStylePatterns))
}
