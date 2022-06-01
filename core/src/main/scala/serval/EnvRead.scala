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

trait EnvRead[T]:
  def read(values: Map[String, String]): EnvLoadResult[T]

object EnvRead:
  def apply[T](using envRead: EnvRead[T]): EnvRead[T] =
    envRead

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

  def or(other: EnvRead[A]): EnvRead[A] =
    new EnvRead[A]:
      def read(values: Map[String, String]): EnvLoadResult[A] =
        envRead.read(values) match {
          case s: EnvLoadResult.Success[A]       => s
          case m: EnvLoadResult.Missing          => other.read(values)
          case p: EnvLoadResult.ParseError       => p
          case a: EnvLoadResult.AggregatedErrors => a
        }

  def default(value: A): EnvRead[A] =
    new EnvRead[A]:
      def read(values: Map[String, String]): EnvLoadResult[A] =
        envRead.read(values) match {
          case s: EnvLoadResult.Success[A] => s
          case m: EnvLoadResult.Missing =>
            EnvLoadResult.Success("<default>", value)
          case p: EnvLoadResult.ParseError       => p
          case a: EnvLoadResult.AggregatedErrors => a
        }
