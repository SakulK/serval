/*
 * Copyright 2022 serval
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

class ServalSuite extends FunSuite {

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
}
