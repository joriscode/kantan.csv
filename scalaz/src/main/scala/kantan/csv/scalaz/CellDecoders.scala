package kantan.csv.scalaz

import export.{export, exports}
import kantan.csv
import kantan.csv.CellDecoder

import scalaz.Maybe._
import scalaz.Scalaz._
import scalaz.{Maybe, Scalaz, \/}

@exports
object CellDecoders {
  @export(Orphan)
  implicit def eitherCellDecoder[A, B](implicit da: CellDecoder[A], db: CellDecoder[B]): CellDecoder[A \/ B] =
    CellDecoder(s ⇒ da.decode(s).map(_.left[B]).orElse(db.decode(s).map(_.right[A])))

  @export(Orphan)
  implicit def maybeDecoder[A](implicit da: CellDecoder[A]): CellDecoder[Maybe[A]] = CellDecoder { s ⇒
    if(s.isEmpty) csv.DecodeResult.success(empty)
    else          da.decode(s).map(just)
  }
}