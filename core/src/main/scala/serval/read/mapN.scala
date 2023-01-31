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

// format: off
given EnvReadMapNExtensions: {} with {
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
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5])
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

extension [A0, A1, A2, A3, A4, A5, A6](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6])
)
  def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6) => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .mapResult { case ((((((a0, a1), a2), a3), a4), a5), a6) =>
            f(a0, a1, a2, a3, a4, a5, a6)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7])
)
  def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7) => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .mapResult { case (((((((a0, a1), a2), a3), a4), a5), a6), a7) =>
            f(a0, a1, a2, a3, a4, a5, a6, a7)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8])
)
  def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8) => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .mapResult {
            case ((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9])
)
  def mapN[B](f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9) => B): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .mapResult {
            case (((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .mapResult {
            case ((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .mapResult {
            case (((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .mapResult {
            case ((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .product(r13.read(values))
          .mapResult {
            case (((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12), a13) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .product(r13.read(values))
          .product(r14.read(values))
          .mapResult {
            case ((((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12), a13), a14) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .product(r13.read(values))
          .product(r14.read(values))
          .product(r15.read(values))
          .mapResult {
            case (((((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12), a13), a14), a15) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .product(r13.read(values))
          .product(r14.read(values))
          .product(r15.read(values))
          .product(r16.read(values))
          .mapResult {
            case ((((((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12), a13), a14), a15), a16) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .product(r13.read(values))
          .product(r14.read(values))
          .product(r15.read(values))
          .product(r16.read(values))
          .product(r17.read(values))
          .mapResult {
            case (((((((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12), a13), a14), a15), a16), a17) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17], EnvRead[A18])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .product(r13.read(values))
          .product(r14.read(values))
          .product(r15.read(values))
          .product(r16.read(values))
          .product(r17.read(values))
          .product(r18.read(values))
          .mapResult {
            case ((((((((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12), a13), a14), a15), a16), a17), a18) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17], EnvRead[A18], EnvRead[A19])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .product(r13.read(values))
          .product(r14.read(values))
          .product(r15.read(values))
          .product(r16.read(values))
          .product(r17.read(values))
          .product(r18.read(values))
          .product(r19.read(values))
          .mapResult {
            case (((((((((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12), a13), a14), a15), a16), a17), a18), a19) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17], EnvRead[A18], EnvRead[A19], EnvRead[A20])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .product(r13.read(values))
          .product(r14.read(values))
          .product(r15.read(values))
          .product(r16.read(values))
          .product(r17.read(values))
          .product(r18.read(values))
          .product(r19.read(values))
          .product(r20.read(values))
          .mapResult {
            case ((((((((((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12), a13), a14), a15), a16), a17), a18), a19), a20) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20)
          }

extension [A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21](
    tuple: (EnvRead[A0], EnvRead[A1], EnvRead[A2], EnvRead[A3], EnvRead[A4], EnvRead[A5], EnvRead[A6], EnvRead[A7], EnvRead[A8], EnvRead[A9], EnvRead[A10], EnvRead[A11], EnvRead[A12], EnvRead[A13], EnvRead[A14], EnvRead[A15], EnvRead[A16], EnvRead[A17], EnvRead[A18], EnvRead[A19], EnvRead[A20], EnvRead[A21])
)
  def mapN[B](
      f: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) => B
  ): EnvRead[B] =
    new EnvRead[B]:
      def read(values: Map[String, String]): EnvLoadResult[B] =
        val (r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21) = tuple
        r0.read(values)
          .product(r1.read(values))
          .product(r2.read(values))
          .product(r3.read(values))
          .product(r4.read(values))
          .product(r5.read(values))
          .product(r6.read(values))
          .product(r7.read(values))
          .product(r8.read(values))
          .product(r9.read(values))
          .product(r10.read(values))
          .product(r11.read(values))
          .product(r12.read(values))
          .product(r13.read(values))
          .product(r14.read(values))
          .product(r15.read(values))
          .product(r16.read(values))
          .product(r17.read(values))
          .product(r18.read(values))
          .product(r19.read(values))
          .product(r20.read(values))
          .product(r21.read(values))
          .mapResult {
            case (((((((((((((((((((((a0, a1), a2), a3), a4), a5), a6), a7), a8), a9), a10), a11), a12), a13), a14), a15), a16), a17), a18), a19), a20), a21) =>
              f(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21)
          }
}
