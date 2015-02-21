package overpoker.model

case class Card (rank: Rank)(suit: SuitName){
  override def toString() = s"$rank of $suit"
}

case class Suit (name: SuitName) {

  private val cardInSuit = Card(_:Rank)(name)

  private def makeRankedCards = {
    for(i <- 2 to 10) yield cardInSuit(Numeric(i))
  }

  val cards: Vector[Card] = makeRankedCards :+ cardInSuit(Ace) :+ cardInSuit(King) :+ cardInSuit(Queen) :+ cardInSuit(Jack) toVector
}
