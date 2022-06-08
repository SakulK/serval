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

trait EnvRead[T]:
  def read(values: Map[String, String]): EnvLoadResult[T]

def env(name: String): EnvRead[String] =
  new EnvRead[String]:
    def read(values: Map[String, String]): EnvLoadResult[String] =
      values.get(name) match {
        case Some(value) => EnvLoadResult.Success(name, value)
        case None        => EnvLoadResult.Failure(EnvLoadError.Missing(name))
      }

def pure[T](value: T): EnvRead[T] =
  new EnvRead[T]:
    def read(values: Map[String, String]): EnvLoadResult[T] =
      EnvLoadResult.Success("<pure>", value)

object EnvRead:
  def apply[T](using envRead: EnvRead[T]): EnvRead[T] =
    envRead

given EnvReadExtensions: {} with {
  extension [A](envRead: EnvRead[A])
    def map[B](f: A => B): EnvRead[B] =
      new EnvRead[B]:
        def read(values: Map[String, String]): EnvLoadResult[B] =
          envRead.read(values) match {
            case EnvLoadResult.Success(name, value) =>
              EnvLoadResult.Success(name, f(value))
            case f: EnvLoadResult.Failure => f
          }

    def as[B](using envParse: EnvParse[A, B]): EnvRead[B] =
      new EnvRead[B]:
        def read(values: Map[String, String]): EnvLoadResult[B] =
          envRead.read(values) match {
            case EnvLoadResult.Success(name, value) =>
              envParse.parse(value) match {
                case Right(parsed) => EnvLoadResult.Success(name, parsed)
                case Left(error) =>
                  EnvLoadResult.Failure(EnvLoadError.ParseError(name, error))
              }
            case f: EnvLoadResult.Failure => f
          }

    def or(other: EnvRead[A]): EnvRead[A] =
      new EnvRead[A]:
        def read(values: Map[String, String]): EnvLoadResult[A] =
          envRead.read(values) match {
            case s: EnvLoadResult.Success[A] => s
            case EnvLoadResult.Failure(EnvLoadError.Missing(name)) =>
              other.read(values) match {
                case EnvLoadResult.Failure(EnvLoadError.Missing(otherName)) =>
                  EnvLoadResult.Failure(
                    EnvLoadError.Missing(s"$name|$otherName")
                  )
                case result => result
              }
            case f: EnvLoadResult.Failure => f
          }

    def default(value: A): EnvRead[A] =
      new EnvRead[A]:
        def read(values: Map[String, String]): EnvLoadResult[A] =
          envRead.read(values) match {
            case s: EnvLoadResult.Success[A] => s
            case EnvLoadResult.Failure(_: EnvLoadError.Missing) =>
              EnvLoadResult.Success("<default>", value)
            case f: EnvLoadResult.Failure => f
          }

    def secret: EnvRead[Secret[A]] =
      new EnvRead[Secret[A]]:
        def read(values: Map[String, String]): EnvLoadResult[Secret[A]] =
          envRead.read(values) match {
            case EnvLoadResult.Success(name, value) =>
              EnvLoadResult.Success(name, Secret(value))
            case f @ EnvLoadResult.Failure(EnvLoadError.Missing(_)) => f
            case EnvLoadResult.Failure(e: EnvLoadError.ParseError) =>
              EnvLoadResult.Failure(
                EnvLoadError.ParseError(e.name, "Failed to parse secret value")
              )
            case EnvLoadResult.Failure(e: EnvLoadError.AggregatedErrors) =>
              EnvLoadResult.Failure(
                EnvLoadError.ParseError(
                  "",
                  "Multiple errors while loading secret value"
                )
              )
          }

  extension [A](envRead: EnvRead[List[A]])
    def asList[B](using envParse: EnvParse[A, B]): EnvRead[List[B]] =
      new EnvRead[List[B]]:
        def read(values: Map[String, String]): EnvLoadResult[List[B]] =
          envRead.read(values) match {
            case EnvLoadResult.Success(name, value) =>
              value.partitionMap(envParse.parse) match {
                case (Nil, parsedValues) =>
                  EnvLoadResult.Success(name, parsedValues)
                case (errors, _) =>
                  EnvLoadResult.Failure(
                    EnvLoadError.ParseError(name, errors.mkString(", "))
                  )
              }
            case f: EnvLoadResult.Failure => f
          }
}
