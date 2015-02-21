package overpoker.model

sealed trait Rank

case object Ace extends Rank
case object King extends Rank
case object Queen extends Rank
case object Jack extends Rank
case class Numeric(number: Int) extends Rank{
  override def toString() = number.toString
}

object Rank{
  implicit def toNumeric(i: Int) = Numeric(i)
}
