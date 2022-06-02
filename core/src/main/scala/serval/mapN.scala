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

extension [A0, A1](tuple: (EnvRead[A0], EnvRead[A1]))
  def mapN[B](f: (A0, A1) => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1) = tuple
        r0.read(values).product(r1.read(values)).mapResult(f.tupled)

extension [A0, A1, A2](tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2]))
  def mapN[B](f: (A0, A1, A2) => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .mapResult { case ((a0, a1), a2) => f(a0, a1, a2) }

extension [A0, A1, A2, A3](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3])
)
  def mapN[B](f: (A0, A1, A2, A3) => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .mapResult { case (((a0, a1), a2), a3) => f(a0, a1, a2, a3) }

extension [A0, A1, A2, A3, A4](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4])
)
  def mapN[B](f: (A0, A1, A2, A3, A4) => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .mapResult { case ((((a0, a1), a2), a3), a4) =>
            f(a0, a1, a2, a3, a4)
          }

extension [A0, A1, A2, A3, A4, A5](
    tuple: (
        EnvRead[A0],
        EnvRead[A1],
        EnvRead[A2],
        EnvRead[A3],
        EnvRead[A4],
        EnvRead[A5]
    )
)
  def mapN[B](f: (A0, A1, A2, A3, A4, A5) => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .mapResult { case (((((a0, a1), a2), a3), a4), a5) =>
            f(a0, a1, a2, a3, a4, a5)
          }
