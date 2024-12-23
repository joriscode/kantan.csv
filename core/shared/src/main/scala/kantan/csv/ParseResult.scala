/*
 * Copyright 2015 Nicolas Rinaudo
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

package kantan.csv

import kantan.codecs.ResultCompanion

/** Provides convenience methods for creating instances of [[ParseResult]]. */
object ParseResult extends ResultCompanion.WithDefault[ParseError] {
  override protected def fromThrowable(t: Throwable): ParseError = ParseError.IOError(t)

  /** Creates a new [[ParseResult]] failure wrapping a [[ParseError.IOError]] error.
    *
    * @example
    *   {{{
    * scala> ParseResult.io(new java.io.IOException("some sort of io error"))
    * res0: ParseResult[Nothing] = Left(IOError: some sort of io error)
    *   }}}
    */
  def io(e: Throwable): ParseResult[Nothing] = failure(ParseError.IOError(e))

  /** Creates a new [[ParseResult]] failure wrapping a [[ParseError.NoSuchElement]] error.
    *
    * @example
    *   {{{
    * scala> ParseResult.noSuchElement
    * res0: ParseResult[Nothing] = Left(NoSuchElement: trying to read from an empty reader)
    *   }}}
    */
  def noSuchElement: ParseResult[Nothing] = failure(ParseError.NoSuchElement)
}
