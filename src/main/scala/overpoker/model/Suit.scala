package overpoker.model

case class Card (rank: Rank, suit: SuitName){
  override def toString() = s"$rank of $suit"
}

case class Suit (name: SuitName) {

  private def makeRankedCards = {
    for(i <- 2 to 10) yield Card(Numeric(i), name)
  }

  val cards: Vector[Card] = makeRankedCards :+ Card(Ace, name) :+ Card(King, name) :+ Card(Queen, name) :+ Card(Jack, name) toVector
}
