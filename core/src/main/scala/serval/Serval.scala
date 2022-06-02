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

def env(name: String): EnvRead[String] =
  new EnvRead[String]:
    def read(values: Map[String, String]): EnvLoadResult[String] =
      values.get(name) match {
        case Some(value) => EnvLoadResult.Success(name, value)
        case None        => EnvLoadResult.Failure(EnvLoadError.Missing(name))
      }

def load[T: EnvRead](values: Map[String, String]): Either[String, T] =
  EnvRead[T].read(values) match {
    case EnvLoadResult.Success(name, value) =>
      Right(value)
    case EnvLoadResult.Failure(EnvLoadError.Missing(name)) =>
      Left(s"Missing $name")
    case EnvLoadResult.Failure(EnvLoadError.ParseError(name, error)) =>
      Left(s"Error while parsing $name: $error")
    case EnvLoadResult.Failure(EnvLoadError.AggregatedErrors(errors)) =>
      Left(s"Errors: $errors")
  }
