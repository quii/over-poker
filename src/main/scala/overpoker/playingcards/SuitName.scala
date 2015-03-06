package overpoker.playingcards

sealed trait Suit{

  val symbol: String

  private def makeRankedCards = for(i <- 2 to 10) yield Card(Numeric(i), this)

  val cards: Vector[Card] = makeRankedCards :+ Card(Ace, this) :+ Card(King, this) :+ Card(Queen, this) :+ Card(Jack, this) toVector

  override def toString = symbol

}

case object Hearts extends Suit{
  val symbol = "♥"
}

case object Diamonds extends Suit{
  val symbol = "♦"
}

case object Spades extends Suit{
  val symbol = "♠"
}

case object Clubs extends Suit{
  val symbol = "♣"
}