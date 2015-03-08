package overpoker.playingcards

case class RankCount(rank: Rank, count: Int)

case class SuitAndRanks(suit: Suit, ranks: Vector[Rank])

case class OrganisedDeck(cards: Vector[Card]){

  def groupBySuit = cards.groupBy(_.suit).map{
    case (suit, cards) => SuitAndRanks(suit, cards.map(_.rank))
  }

  def groupByRank = cards.groupBy(_.rank).map{
    case (rank, cards) => RankCount(rank, cards.size)
  }

}

object OrganisedDeck{
  implicit def fromVector(x: Vector[Card]) = OrganisedDeck(x)
}