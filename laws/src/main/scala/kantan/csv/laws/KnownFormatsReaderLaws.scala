/*
 * Copyright 2017 Nicolas Rinaudo
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

package kantan.csv.laws

import kantan.csv.RowCodec
import kantan.csv.engine.ReaderEngine
import kantan.csv.laws.KnownFormatsReaderLaws.Car
import kantan.csv.ops._

trait KnownFormatsReaderLaws {
  implicit def engine: ReaderEngine

  implicit val carFormat = RowCodec.caseCodec(1, 2, 3, 4, 0)(Car.apply)(Car.unapply)

  def read(res: String): List[Car] =
    getClass.getResource(s"/known_formats/$res.csv").unsafeReadCsv(header = true)

  lazy val reference: List[Car] = read("raw")

  def excelMac12_0: Boolean = read("excel_mac_12_0") == reference

  def numbers1_0_3: Boolean = read("numbers_1_0_3") == reference

  def googleDocs: Boolean = read("google_docs") == reference
}

object KnownFormatsReaderLaws {
  case class Car(make: String, model: String, description: Option[String], price: Int, year: Int)
}
