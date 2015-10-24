package overpoker.playingcards

import scala.language.implicitConversions

sealed trait Rank{
  def of(suit: Suit) = Card(this, suit)
}

case object Ace extends Rank
case object King extends Rank
case object Queen extends Rank
case object Jack extends Rank
case class Numeric(number: Int) extends Rank{
  override def toString = number.toString
}
case object AceLow extends Rank

object Rank {

  implicit def toNumeric(i: Int): Numeric = Numeric(i)

  implicit def toInt(rank: Rank): Int = rank match{
    case Ace => 14
    case AceLow => 1
    case King => 13
    case Queen => 12
    case Jack => 11
    case Numeric(n) => n
  }

}
