package overpoker.model


sealed trait HandValue

case class Pair(rank: Rank) extends HandValue
case object ThreeOfAKind extends HandValue
case class HighCard(highest: Int, second: Int) extends HandValue

object HandValue{

  def getValue(hand: Hand, table: List[Card]): HandValue = {
    val allCards = table :+ hand.card1 :+ hand.card2

    val twoOfAKinds= allCards
      .groupBy(card => card.rank)
      .collect{case (rank, List(card1, card2, _*))=> rank}
      .toList
      .sortBy(r => -Rank.toInt(r))

    twoOfAKinds.headOption.map(Pair).getOrElse(HighCard(hand.card1.rank, hand.card2.rank))
  }
}