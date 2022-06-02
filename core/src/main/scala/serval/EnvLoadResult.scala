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

enum EnvLoadError:
  case Missing(name: String) extends EnvLoadError
  case ParseError(name: String, error: String) extends EnvLoadError
  case AggregatedErrors(errors: List[EnvLoadError]) extends EnvLoadError

enum EnvLoadResult[+A]:
  case Success(name: String, value: A) extends EnvLoadResult[A]
  case Failure(error: EnvLoadError) extends EnvLoadResult[Nothing]

extension [A](ra: EnvLoadResult[A])
  def product[B](rb: EnvLoadResult[B]): EnvLoadResult[(A, B)] =
    (ra, rb) match {
      case (
            EnvLoadResult.Success(nameA, valueA),
            EnvLoadResult.Success(nameB, valueB)
          ) =>
        EnvLoadResult.Success(s"$nameA, $nameB", (valueA, valueB))

      case (failure: EnvLoadResult.Failure, _: EnvLoadResult.Success[?]) =>
        failure

      case (_: EnvLoadResult.Success[?], failure: EnvLoadResult.Failure) =>
        failure

      case (EnvLoadResult.Failure(error1), EnvLoadResult.Failure(error2)) =>
        EnvLoadResult.Failure(combineErrors(error1, error2))
    }

  def mapResult[B](f: A => B): EnvLoadResult[B] =
    ra match {
      case EnvLoadResult.Success(name, value) =>
        EnvLoadResult.Success(name, f(value))
      case failure: EnvLoadResult.Failure => failure
    }

def combineErrors(error1: EnvLoadError, error2: EnvLoadError): EnvLoadError =
  (error1, error2) match {
    case (
          EnvLoadError.AggregatedErrors(list1),
          EnvLoadError.AggregatedErrors(list2)
        ) =>
      EnvLoadError.AggregatedErrors(list1 ++ list2)

    case (EnvLoadError.AggregatedErrors(list), other) =>
      EnvLoadError.AggregatedErrors(other :: list)

    case (other, EnvLoadError.AggregatedErrors(list)) =>
      EnvLoadError.AggregatedErrors(other :: list)

    case (error1, error2) =>
      EnvLoadError.AggregatedErrors(List(error1, error2))
  }
