## serval

**S**imple **E**nvironment **R**ead **VAL**ues (yes, I tried my best to come up with a name that's also an animal) - Scala library to read variables defined in ENV, inspired by [Ciris](https://cir.is/) which is awesome and I cannot recommend it enough.
I wanted to replicate the experience of using Ciris to read ENV variables but in applications that (unfortunately) don't use cats-effect. Serval has zero dependencies and it's sole purpose is to read a `Map[String, String]` (like `sys.env`) into user-defined config case class(es).


### Usage

This library is written in Scala 3 (mostly because I wanted to try it out) but can be used from 2.13.6+

To use the latest version in a Scala 3 project, include the following in your `build.sbt`:

```scala
libraryDependencies ++= Seq(
  "io.github.sakulk" %% "serval-core" % "@VERSION@"
)
```

For Scala 2.13.6+ you need to add the `-Ytasty-reader` to your projects `scalacOptions` and include the following dependency:

```scala
libraryDependencies ++= Seq(
  "io.github.sakulk" %% "serval-legacy" % "@VERSION@"
)
```

### Minimal example

Scala 3:
```scala mdoc

case class MyConfig(foo: String, bar: Int, baz: String)

object MyConfig:
  import serval.read.{given, *}
  given EnvRead[MyConfig] =
    (
      env("CONFIG_FOO").or(env("CONFIG_FOO_2")),
      env("CONFIG_BAR").as[Int],
      env("CONFIG_BAZ").default("bazinga")
    ).mapN(MyConfig.apply)

serval.load[MyConfig](
  // use sys.env to load real ENV variables
  Map(
    "CONFIG_FOO" -> "foo",
    "CONFIG_BAR" -> "42"
  )
)

serval.load[MyConfig](
  Map("CONFIG_BAR" -> "bar")
)
```

Scala 2.13:
```scala mdoc:reset
case class MyConfig(foo: String, bar: Int, baz: String)

object MyConfig {
  import serval.read.legacy._
  implicit val envRead: EnvRead[MyConfig] =
    (
      env("CONFIG_FOO").or(env("CONFIG_FOO_2")),
      env("CONFIG_BAR").as[Int],
      env("CONFIG_BAZ").default("bazinga")
    ).mapN(MyConfig.apply)
}

serval.legacy.load[MyConfig](
  // use sys.env to load real ENV variables
  Map(
    "CONFIG_FOO" -> "foo",
    "CONFIG_BAR" -> "42"
  )
)
```

