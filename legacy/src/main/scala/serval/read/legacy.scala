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

package serval.read

object legacy:
  type EnvRead[T] = serval.read.EnvRead[T]
  val EnvRead = serval.read.EnvRead

  type EnvParse[A, B] = serval.read.EnvParse[A, B]
  val EnvParse = serval.read.EnvParse

  def env(name: String): EnvRead[String] = serval.read.env(name)
  def pure[T](value: T): EnvRead[T] = serval.read.pure(value)

  implicit class EnvReadOps[A](private val envRead: EnvRead[A]) extends AnyVal:
    def map[B](f: A => B): EnvRead[B] = EnvReadExtensions.map(envRead)(f)
    def as[B](using envParse: EnvParse[A, B]): EnvRead[B] =
      EnvReadExtensions.as(envRead)[B]
    def or(other: EnvRead[A]): EnvRead[A] = EnvReadExtensions.or(envRead)(other)
    def default(value: A): EnvRead[A] =
      EnvReadExtensions.default(envRead)(value)
    def secret: EnvRead[Secret[A]] = EnvReadExtensions.secret(envRead)

  implicit class EnvParseOps[A, B](envParse: EnvParse[A, B]) extends AnyVal:
    def map[C](f: B => C): EnvParse[A, C] = EnvParseExtensions.map(envParse)(f)
 
  // format: off
  implicit class EnvReadTuple2Ops[A0, A1](tuple: (EnvRead[A0], EnvRead[A1])) extends AnyVal:
    def mapN[B](f: (A0, A1) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple3Ops[A0, A1, A2](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple4Ops[A0, A1, A2, A3](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple5Ops[A0, A1, A2, A3, A4](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple6Ops[A0, A1, A2, A3, A4, A5](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple7Ops[A0, A1, A2, A3, A4, A5, A6](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple8Ops[A0, A1, A2, A3, A4, A5, A6, A7](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple9Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple10Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple11Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple12Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple13Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple14Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple15Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple16Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple17Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple18Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple19Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17], EnvRead[A18])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple20Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17], EnvRead[A18], EnvRead[A19])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple21Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17], EnvRead[A18], EnvRead[A19], EnvRead[A20])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)

  implicit class EnvReadTuple22Ops[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17], EnvRead[A18], EnvRead[A19], EnvRead[A20], EnvRead[A21])) extends AnyVal:
    def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) => B): EnvRead[B] = EnvReadMapNExtensions.mapN(tuple)(f)
