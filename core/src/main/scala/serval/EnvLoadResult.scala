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

enum EnvLoadResult[+A]:
  case Success(name: String, value: A) extends EnvLoadResult[A]
  case Missing(name: String) extends EnvLoadResult[Nothing]
  case ParseError(name: String, error: String) extends EnvLoadResult[Nothing]
  case AggregatedErrors(errors: List[EnvLoadResult[Nothing]])
      extends EnvLoadResult[Nothing]
