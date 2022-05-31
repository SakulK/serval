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

enum EnvLoadResult[+T]:
  case Success(name: String, value: T) extends EnvLoadResult[T]
  case Missing(name: String) extends EnvLoadResult[Nothing]
  case ParseError(name: String, error: String) extends EnvLoadResult[Nothing]
  case AggregatedErrors(errors: List[EnvLoadResult[Nothing]])
      extends EnvLoadResult[Nothing]

trait EnvRead[T]:
  def read(values: Map[String, String]): EnvLoadResult[T]

object EnvRead:
  def apply[T](using envRead: EnvRead[T]): EnvRead[T] =
    envRead

def env(name: String): EnvRead[String] =
  new EnvRead[String]:
    def read(values: Map[String, String]): EnvLoadResult[String] =
      values.get(name) match {
        case Some(value) => EnvLoadResult.Success(name, value)
        case None        => EnvLoadResult.Missing(name)
      }

def load[T: EnvRead](values: Map[String, String]): Either[String, T] =
  EnvRead[T].read(values) match {
    case EnvLoadResult.Success(name, value) => Right(value)
    case EnvLoadResult.Missing(name)        => Left(s"Missing $name")
    case EnvLoadResult.ParseError(name, error) =>
      Left(s"Error while parsing $name: $error")
    case EnvLoadResult.AggregatedErrors(errors) => Left(s"Errors: $errors")
  }

extension [A](envRead: EnvRead[A])
  def map[B](f: A => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        envRead.read(values) match {
          case EnvLoadResult.Success(name, value) =>
            EnvLoadResult.Success(name, f(value))
          case m: EnvLoadResult.Missing          => m
          case p: EnvLoadResult.ParseError       => p
          case a: EnvLoadResult.AggregatedErrors => a
        }

  def as[B](using envParse: EnvParse[A, B]): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        envRead.read(values) match {
          case EnvLoadResult.Success(name, value) =>
            envParse.parse(value) match {
              case Right(parsed) => EnvLoadResult.Success(name, parsed)
              case Left(error)   => EnvLoadResult.ParseError(name, error)
            }
          case m: EnvLoadResult.Missing          => m
          case p: EnvLoadResult.ParseError       => p
          case a: EnvLoadResult.AggregatedErrors => a
        }

trait EnvParse[A, B]:
  def parse(input: A): Either[String, B]

object EnvParse:

  def apply[A, B](using envParse: EnvParse[A, B]): EnvParse[A, B] = envParse

  given EnvParse[String, Int] with
    def parse(input: String): Either[String, Int] =
      input.toIntOption match {
        case Some(value) => Right(value)
        case None        => Left(s"Failed to parse Int from \"$input\"")
      }
