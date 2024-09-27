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

import serval.read.EnvLoadError

class EnvLoadException(val error: EnvLoadError)
    extends RuntimeException(EnvLoadException.createMessage(error))

object EnvLoadException:
  def createMessage(error: EnvLoadError): String =
    s"Failed to load config from environment:\n${renderError(error)}"

  def renderError(error: EnvLoadError): String =
    error match
      case EnvLoadError.Missing(name)           => s" - $name: Variable missing"
      case EnvLoadError.ParseError(name, error) => s" - $name: $error"
      case EnvLoadError.AggregatedErrors(missing, parseErrors) =>
        (missing ++ parseErrors).map(renderError).mkString("\n")
