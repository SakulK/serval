// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.7" // your current series x.y

ThisBuild / organization := "io.github.sakulk"
ThisBuild / organizationName := "serval"
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / startYear := Some(2023)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("SakulK", "Łukasz Krenski")
)

ThisBuild / sonatypeCredentialHost := xerial.sbt.Sonatype.sonatype01

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

val Scala3 = "3.3.4"
ThisBuild / scalaVersion := Scala3

lazy val root = tlCrossRootProject.aggregate(core, legacy)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "serval-core",
    libraryDependencies ++= Seq(
      "org.scalameta" %%% "munit" % "1.0.2" % Test
    )
  )

lazy val legacy = crossProject(JVMPlatform, JSPlatform, NativePlatform)
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
    tlSiteHelium ~= {
      import laika.helium.config._
      _.site.mainNavigation(appendLinks =
        Seq(
          ThemeNavigationSection(
            "Related Projects",
            TextLink.external("https://cir.is/", "Ciris")
          )
        )
      )
    }
  )
  .dependsOn(legacy.jvm)
