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

package serval.read

import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.util.Try

trait EnvParse[A, B]:
  def parse(input: A): Either[String, B]

object EnvParse:

  def apply[A, B](using envParse: EnvParse[A, B]): EnvParse[A, B] = envParse

  def fromOption[A, B](typeName: String)(f: A => Option[B]): EnvParse[A, B] =
    new EnvParse[A, B]:
      def parse(input: A): Either[String, B] =
        f(input) match
          case Some(value) => Right(value)
          case None        => Left(s"Failed to parse $typeName from \"$input\"")

  def fromEither[A, B](f: A => Either[String, B]): EnvParse[A, B] =
    new EnvParse[A, B]:
      def parse(input: A): Either[String, B] = f(input)

  given EnvParse[String, Int] = fromOption("Int")(_.toIntOption)
  given EnvParse[String, Long] = fromOption("Long")(_.toLongOption)
  given EnvParse[String, Double] = fromOption("Double")(_.toDoubleOption)
  given EnvParse[String, Float] = fromOption("Float")(_.toFloatOption)
  given EnvParse[String, Boolean] = fromOption("Boolean")(_.toBooleanOption)
  given EnvParse[String, Duration] =
    fromOption("Duration")(s => Try(Duration(s)).toOption)
  given EnvParse[String, FiniteDuration] =
    fromOption("FiniteDuration") { s =>
      Try(Duration(s)).toOption.collect { case fd: FiniteDuration => fd }
    }

given EnvParseExtensions: {} with
  extension [A, B](envParse: EnvParse[A, B])
    def map[C](f: B => C): EnvParse[A, C] =
      new EnvParse[A, C]:
        def parse(input: A): Either[String, C] =
          envParse.parse(input).map(f)
