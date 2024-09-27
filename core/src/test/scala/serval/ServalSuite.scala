/*
 * Copyright 2023 serval
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package serval

import munit.*
import scala.concurrent.duration.*
import serval.read.{given, *}

class ServalSuite extends FunSuite:

  case class SimpleConfig(a: Int)

  given EnvRead[SimpleConfig] = env("CONFIG_A").as[Int].map(SimpleConfig.apply)

  test("SimpleConfig success") {
    val result = load[SimpleConfig](Map("CONFIG_A" -> "42"))
    assertEquals(result, Right(SimpleConfig(42)))
  }

  test("SimpleConfig missing") {
    val result = load[SimpleConfig](Map("WRONG" -> "1"))
    assert(result.isLeft)
  }

  test("SimpleConfig parse error") {
    val result = load[SimpleConfig](Map("CONFIG_A" -> "not and int"))
    assert(result.isLeft)
  }

  case class OrConfig(a: Int)

  given EnvRead[OrConfig] =
    env("CONFIG_A").or(env("CONFIG_A_ALT")).as[Int].map(OrConfig.apply)

  test("OrConfig success on first") {
    val result = load[OrConfig](Map("CONFIG_A" -> "1", "CONFIG_A_ALT" -> "2"))
    assertEquals(result, Right(OrConfig(1)))
  }

  test("OrConfig success on second") {
    val result = load[OrConfig](Map("CONFIG_A_ALT" -> "2"))
    assertEquals(result, Right(OrConfig(2)))
  }

  test("OrConfig missing") {
    val result = load[OrConfig](Map("INVALID" -> "2"))
    assert(result.isLeft)
  }

  test("OrConfig first fails to parse") {
    val result = load[OrConfig](Map("CONFIG_A" -> "a", "CONFIG_A_ALT" -> "2"))
    assert(result.isLeft)
  }

  case class DefaultConfig(a: Int)

  given EnvRead[DefaultConfig] =
    env("CONFIG_A").as[Int].default(10).map(DefaultConfig.apply)

  test("DefaultConfig success") {
    val result = load[DefaultConfig](Map("CONFIG_A" -> "1"))
    assertEquals(result, Right(DefaultConfig(1)))
  }

  test("DefaultConfig missing, use default") {
    val result = load[DefaultConfig](Map("INVALID" -> "2"))
    assertEquals(result, Right(DefaultConfig(10)))
  }

  test("DefaultConfig parse error") {
    val result = load[DefaultConfig](Map("CONFIG_A" -> "text"))
    assert(result.isLeft)
  }

  case class TwoValues(a: Int, b: String)

  given EnvRead[TwoValues] =
    (
      env("CONFIG_A").as[Int],
      env("CONFIG_B")
    ).mapN(TwoValues.apply)

  test("TwoValues success") {
    val result = load[TwoValues](Map("CONFIG_A" -> "4", "CONFIG_B" -> "b"))
    assertEquals(result, Right(TwoValues(4, "b")))
  }

  test("TwoValues missing one") {
    val result = load[TwoValues](Map("CONFIG_A" -> "4", "CONFIG_BAR" -> "b"))
    assert(result.isLeft)
  }

  test("TwoValues parse error") {
    val result = load[TwoValues](Map("CONFIG_A" -> "a", "CONFIG_B" -> "b"))
    assert(result.isLeft)
  }

  case class DurationNewtype(d: FiniteDuration)
  given EnvParse[String, DurationNewtype] =
    EnvParse[String, FiniteDuration].map(DurationNewtype.apply)

  case class WithNewtype(d: DurationNewtype)
  given EnvRead[WithNewtype] =
    env("DURATION").as[DurationNewtype].map(WithNewtype.apply)

  test("WithNewtype duration success") {
    val result = load[WithNewtype](Map("DURATION" -> "5 seconds"))
    assertEquals(result, Right(WithNewtype(DurationNewtype(5.seconds))))
  }

  test("WithNewtype duration parse error") {
    val result = load[WithNewtype](Map("DURATION" -> "not a duration"))
    assert(result.isLeft)
  }

  case class PureValues(a: String, b: Int)
  given EnvRead[PureValues] =
    (
      pure("text"),
      pure(5)
    ).mapN(PureValues.apply)

  test("PureValues") {
    val result = load[PureValues](Map.empty)
    assertEquals(result, Right(PureValues("text", 5)))
  }

  case class SecretConfig(key: Secret[String])
  given EnvRead[SecretConfig] = env("KEY").secret.map(SecretConfig.apply)

  test("SecretConfig") {
    val result = load[SecretConfig](Map("KEY" -> "123"))
    assertEquals(result, Right(SecretConfig(Secret("123"))))
  }

  test("Secret toString") {
    assertEquals(SecretConfig(Secret("123")).toString, "SecretConfig(<secret>)")
  }

  case class ListOfBoolConfig(list: List[Boolean])
  given EnvRead[ListOfBoolConfig] = env("BOOLS")
    .splitTrimAll(",")
    .asList[Boolean]
    .map(ListOfBoolConfig.apply)

  test("ListOfBool success") {
    val result =
      load[ListOfBoolConfig](Map("BOOLS" -> " true,false,false ,true"))
    assertEquals(
      result,
      Right(ListOfBoolConfig(List(true, false, false, true)))
    )
  }

  test("ListOfBool parse error") {
    val result = load[ListOfBoolConfig](Map("BOOLS" -> "true,foo,false"))
    assert(result.isLeft)
  }

  case class VariablesWithPrefix(list: List[String])
  given EnvRead[VariablesWithPrefix] =
    envWithPrefix("PREFIX_").map(VariablesWithPrefix.apply)

  test("VariablesWithPrefix success") {
    val result = load[VariablesWithPrefix](
      Map("PREFIX_2" -> "2", "PREFIX_1" -> "1", "NOT_PREFIX_A" -> "foo")
    )
    assertEquals(result, Right(VariablesWithPrefix(List("1", "2"))))
  }

  test("VariablesWithPrefix empty") {
    val result = load[VariablesWithPrefix](Map.empty)
    assertEquals(result, Right(VariablesWithPrefix(List())))
  }

  case class DefaultInstanceConfig(a: String, b: String)
  given EnvRead[DefaultInstanceConfig] =
    (
      env("CONFIG_A"),
      env("CONFIG_B")
    ).mapN(DefaultInstanceConfig.apply).default(DefaultInstanceConfig("a", "b"))

  test("DefaultInstanceConfig all missing") {
    val result = load[DefaultInstanceConfig](Map.empty)
    assertEquals(result, Right(DefaultInstanceConfig("a", "b")))
  }

  case class FlatMapConfig(b: String)
  given EnvRead[FlatMapConfig] =
    env("CONFIG_NAME")
      .flatMap { name =>
        env(name)
      }
      .map(FlatMapConfig.apply)

  test("FlatMapConfig success") {
    val result =
      load[FlatMapConfig](Map("CONFIG_NAME" -> "OTHER", "OTHER" -> "foo"))
    assertEquals(result, Right(FlatMapConfig("foo")))
  }

  test("FlatMapConfig missing") {
    val result = load[FlatMapConfig](Map("CONFIG_NAME" -> "OTHER"))
    assert(result.isLeft)
  }

  case class ForComprehensionConfig(a: String, b: String)
  given EnvRead[ForComprehensionConfig] =
    for
      a <- env("CONFIG_A")
      b <- env("CONFIG_B")
    yield ForComprehensionConfig(a, b)

  test("ForComprehensionConfig success") {
    val result = load[ForComprehensionConfig](
      Map("CONFIG_A" -> "foo", "CONFIG_B" -> "bar")
    )
    assertEquals(result, Right(ForComprehensionConfig("foo", "bar")))
  }

  case class OptionConfig(a: Option[String])
  given EnvRead[OptionConfig] =
    env("MAYBE").option.map(OptionConfig.apply)

  test("OptionConfig Some") {
    val result = load[OptionConfig](Map("MAYBE" -> "foo"))
    assertEquals(result, Right(OptionConfig(Some("foo"))))
  }

  test("OptionConfig None") {
    val result = load[OptionConfig](Map.empty)
    assertEquals(result, Right(OptionConfig(None)))
  }

  case class DerivedConfig(simple: SimpleConfig, two: TwoValues) derives EnvRead

  test("Derived config success") {
    val result =
      load[DerivedConfig](Map("CONFIG_A" -> "42", "CONFIG_B" -> "bar"))
    assertEquals(
      result,
      Right(DerivedConfig(SimpleConfig(42), TwoValues(42, "bar")))
    )
  }
