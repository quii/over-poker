package overpoker.model

sealed trait Rank

case object Ace extends Rank
case object King extends Rank
case object Queen extends Rank
case object Jack extends Rank
case class Numeric(number: Int) extends Rank{
  override def toString() = number.toString
}

object Rank {

  implicit def toNumeric(i: Int) = Numeric(i)

  implicit def toInt(rank: Rank) = rank match{
    case Ace => 13
    case King => 12
    case Queen => 11
    case Jack => 10
    case Numeric(n) => n
  }
}
