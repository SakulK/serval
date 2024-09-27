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

import scala.deriving.Mirror
import scala.compiletime.*

trait EnvRead[T]:
  def read(values: Map[String, String]): EnvLoadResult[T]

def env(name: String): EnvRead[String] =
  new EnvRead[String]:
    def read(values: Map[String, String]): EnvLoadResult[String] =
      values.get(name) match
        case Some(value) => EnvLoadResult.Success(name, value)
        case None        => EnvLoadResult.Failure(EnvLoadError.Missing(name))

def envWithPrefix(prefix: String): EnvRead[List[String]] =
  new EnvRead[List[String]]:
    def read(values: Map[String, String]): EnvLoadResult[List[String]] =
      EnvLoadResult.Success(
        s"$prefix*",
        values.view
          .filter(_._1.startsWith(prefix))
          .toList
          .sortBy(_._1)
          .map(_._2)
      )

def pure[T](value: T): EnvRead[T] =
  new EnvRead[T]:
    def read(values: Map[String, String]): EnvLoadResult[T] =
      EnvLoadResult.Success("<pure>", value)

object EnvRead:
  def apply[T](using envRead: EnvRead[T]): EnvRead[T] =
    envRead

  inline def derived[T](using m: Mirror.Of[T]): EnvRead[T] =
    inline m match
      case s: Mirror.SumOf[T] => error("Only product types are supported")
      case p: Mirror.ProductOf[T] =>
        lazy val elemInstances = summonInstances[T, m.MirroredElemTypes]
        new EnvRead[T]:
          def read(values: Map[String, String]): EnvLoadResult[T] =
            val elemResults = elemInstances.map(_.read(values))
            val (elemErrors, elemSuccesses) = elemResults.partitionMap {
              case success: EnvLoadResult.Success[?] => Right(success)
              case EnvLoadResult.Failure(error)      => Left(error)
            }
            if elemErrors.nonEmpty then
              EnvLoadResult.Failure(elemErrors.reduce(combineErrors))
            else
              EnvLoadResult.Success(
                name = elemSuccesses.map(_.name).mkString(", "),
                value = p.fromTuple(
                  Tuple
                    .fromArray(elemSuccesses.map(_.value).toArray)
                    .asInstanceOf[p.MirroredElemTypes]
                )
              )

  inline def summonInstances[T, Elems <: Tuple]: List[EnvRead[?]] =
    inline erasedValue[Elems] match
      case _: (elem *: elems) =>
        summonInline[EnvRead[elem]] :: summonInstances[T, elems]
      case _: EmptyTuple => Nil

given EnvReadExtensions: {} with
  extension [A](envRead: EnvRead[A])
    def map[B](f: A => B): EnvRead[B] =
      new EnvRead[B]:
        def read(values: Map[String, String]): EnvLoadResult[B] =
          envRead.read(values) match
            case EnvLoadResult.Success(name, value) =>
              EnvLoadResult.Success(name, f(value))
            case f: EnvLoadResult.Failure => f

    def flatMap[B](f: A => EnvRead[B]): EnvRead[B] =
      new EnvRead[B]:
        def read(values: Map[String, String]): EnvLoadResult[B] =
          envRead.read(values) match
            case EnvLoadResult.Success(name, value) =>
              f(value).read(values)
            case f: EnvLoadResult.Failure => f

    def as[B](using envParse: EnvParse[A, B]): EnvRead[B] =
      new EnvRead[B]:
        def read(values: Map[String, String]): EnvLoadResult[B] =
          envRead.read(values) match
            case EnvLoadResult.Success(name, value) =>
              envParse.parse(value) match
                case Right(parsed) => EnvLoadResult.Success(name, parsed)
                case Left(error) =>
                  EnvLoadResult.Failure(EnvLoadError.ParseError(name, error))
            case f: EnvLoadResult.Failure => f

    def or(other: EnvRead[A]): EnvRead[A] =
      new EnvRead[A]:
        def read(values: Map[String, String]): EnvLoadResult[A] =
          envRead.read(values) match
            case s: EnvLoadResult.Success[A] => s
            case EnvLoadResult.Failure(EnvLoadError.Missing(name)) =>
              other.read(values) match
                case EnvLoadResult.Failure(EnvLoadError.Missing(otherName)) =>
                  EnvLoadResult.Failure(
                    EnvLoadError.Missing(s"$name|$otherName")
                  )
                case result => result
            case EnvLoadResult.Failure(EnvLoadError.AggregatedErrors(_, Nil)) =>
              other.read(values)
            case f: EnvLoadResult.Failure => f

    def default(value: A): EnvRead[A] =
      new EnvRead[A]:
        def read(values: Map[String, String]): EnvLoadResult[A] =
          envRead.read(values) match
            case s: EnvLoadResult.Success[A] => s
            case EnvLoadResult.Failure(
                  EnvLoadError.Missing(_) |
                  EnvLoadError.AggregatedErrors(_, Nil)
                ) =>
              EnvLoadResult.Success("<default>", value)
            case f: EnvLoadResult.Failure => f

    def option: EnvRead[Option[A]] =
      envRead.map(Option.apply).default(Option.empty)

    def secret: EnvRead[Secret[A]] =
      new EnvRead[Secret[A]]:
        def read(values: Map[String, String]): EnvLoadResult[Secret[A]] =
          envRead.read(values) match
            case EnvLoadResult.Success(name, value) =>
              EnvLoadResult.Success(name, Secret(value))
            case f @ EnvLoadResult.Failure(EnvLoadError.Missing(_)) => f
            case EnvLoadResult.Failure(e: EnvLoadError.ParseError) =>
              EnvLoadResult.Failure(
                EnvLoadError.ParseError(e.name, "Failed to parse secret value")
              )
            case EnvLoadResult.Failure(
                  EnvLoadError.AggregatedErrors(missing, parseErrors)
                ) =>
              EnvLoadResult.Failure(
                EnvLoadError.AggregatedErrors(
                  missing,
                  parseErrors.map(
                    _.copy(error = "Failed to parse secret value")
                  )
                )
              )

  extension [A](envRead: EnvRead[List[A]])
    def asList[B](using envParse: EnvParse[A, B]): EnvRead[List[B]] =
      new EnvRead[List[B]]:
        def read(values: Map[String, String]): EnvLoadResult[List[B]] =
          envRead.read(values) match
            case EnvLoadResult.Success(name, value) =>
              value.partitionMap(envParse.parse) match
                case (Nil, parsedValues) =>
                  EnvLoadResult.Success(name, parsedValues)
                case (errors, _) =>
                  EnvLoadResult.Failure(
                    EnvLoadError.ParseError(name, errors.mkString(", "))
                  )
            case f: EnvLoadResult.Failure => f

  extension (envRead: EnvRead[String])
    def split(delimiter: String): EnvRead[List[String]] =
      envRead.map(_.split(delimiter).toList)

    def splitTrimAll(delimiter: String): EnvRead[List[String]] =
      envRead.split(delimiter).map(_.map(_.trim))
