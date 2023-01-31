// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.4" // your current series x.y

ThisBuild / organization := "io.github.sakulk"
ThisBuild / organizationName := "serval"
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("SakulK", "Łukasz Krenski")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

val Scala3 = "3.2.1"
ThisBuild / scalaVersion := Scala3

lazy val root = tlCrossRootProject.aggregate(core, legacy)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "serval-core",
    libraryDependencies ++= Seq(
      "org.scalameta" %%% "munit" % "0.7.29" % Test
    )
  )

lazy val legacy = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("legacy"))
  .settings(
    name := "serval-legacy"
  )
  .dependsOn(core)

lazy val docs = project
  .in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .settings(
    tlSiteRelatedProjects := Seq(
      "Ciris" -> url("https://cir.is/")
    )
  )
  .dependsOn(legacy.jvm)
