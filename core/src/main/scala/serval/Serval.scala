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

import serval.read.{EnvRead, EnvLoadResult, EnvLoadError}

def load[T: EnvRead](values: Map[String, String]): Either[EnvLoadException, T] =
  EnvRead[T].read(values) match {
    case EnvLoadResult.Success(name, value) =>
      Right(value)
    case EnvLoadResult.Failure(error) =>
      Left(EnvLoadException(error))
  }

def loadOrThrow[T: EnvRead](values: Map[String, String]): T =
  load[T](values).fold(throw _, identity)
