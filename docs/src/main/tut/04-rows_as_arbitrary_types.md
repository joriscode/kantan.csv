---
layout: default
title:  "Decoding rows as arbitrary types"
section: tutorial
---
Other tutorials covered decoding rows as [collections](01-rows_as_collections.html), [tuples](02-rows_as_tuples.html)
and [case classes](03-rows_as_case_classes.html). While those are the most common scenarios, it is sometimes necessary
to decode rows into types that are none of these.

Let's take the same example we did before:

```
Year,Make,Model,Description,Price
1997,Ford,E350,"ac, abs, moon",3000.00
1999,Chevy,"Venture ""Extended Edition""","",4900.00
1999,Chevy,"Venture ""Extended Edition, Very Large""",,5000.00
1996,Jeep,Grand Cherokee,"MUST SELL!
air, moon roof, loaded",4799.00
```

This data is available here:

```tut:silent
val rawData: java.net.URL = getClass.getResource("/wikipedia.csv")
```

Now, let's imagine we have a class that is not a case class into which we'd like to decode each row:

```tut:silent
class Car(val year: Int, val make: String, val model: String, val desc: Option[String], val price: Float) {
  override def toString = s"Car($year, $make, $model, $desc, $price)"
}
```

Providing decoding support for our `Car` class is as simple as implementing an instance of [`RowDecoder`] for it
and marking it as implicit.

There are various ways to implement an instance of [`RowDecoder`], but by far the most idiomatic is to use one of 
the various helper methods defined in its [companion object]({{ site.baseurl }}/api/#kantan.csv.RowDecoder$). For our 
current task, we need to decode a row into 5 values and stick them into `Car`'s constructor: we want [`decoder5`].

```tut:silent
import kantan.csv.ops._
import kantan.csv._

implicit val carDecoder = RowDecoder.decoder5 { (y: Int, m: String, mo: String, d: Option[String], p: Float) =>
  new Car(y, m, mo, d, p)
}(0, 1, 2, 3, 4)
```

And we can now decode our data as usual:

```tut
rawData.asCsvReader[Car](',', true).foreach(println _)
```

The main reason this is the preferred solution is that it allows us never to have to think about individual cells in a
row and how to decode them - we just have to describe what type we're expecting and let kantan.csv deal with decoding
for us.
 
Note that [`decoder5`] also takes a list of indexes as parameter - these map each parameter to a index in a CSV row.
If, as is our case here, there's an exact mapping between the parameters of our construction function and the cells
of each CSV row, the [`ordered5`] method is slightly easier to call.

[`RowDecoder`]:{{ site.baseurl }}/api/#kantan.csv.package@RowDecoder[A]=kantan.codecs.Decoder[Seq[String],A,kantan.csv.DecodeError,kantan.csv.codecs.type]
[`decoder5`]:{{ site.baseurl }}/api/#kantan.csv.RowDecoder$@decoder5[A1,A2,A3,A4,A5,R](f:(A1,A2,A3,A4,A5)=>R)(i1:Int,i2:Int,i3:Int,i4:Int,i5:Int)(implicita1:kantan.csv.CellDecoder[A1],implicita2:kantan.csv.CellDecoder[A2],implicita3:kantan.csv.CellDecoder[A3],implicita4:kantan.csv.CellDecoder[A4],implicita5:kantan.csv.CellDecoder[A5]):kantan.csv.RowDecoder[R]
[`ordered5`]:{{ site.baseurl }}/api/#kantan.csv.RowDecoder$@ordered5[A1,A2,A3,A4,A5,R](f:(A1,A2,A3,A4,A5)=>R)(implicita1:kantan.csv.CellDecoder[A1],implicita2:kantan.csv.CellDecoder[A2],implicita3:kantan.csv.CellDecoder[A3],implicita4:kantan.csv.CellDecoder[A4],implicita5:kantan.csv.CellDecoder[A5]):kantan.csv.RowDecoder[R]