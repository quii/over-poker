package overpoker.model


sealed trait HandValue

case class Pair(rank: Rank) extends HandValue
case class TwoPair(highest: Rank, lowest: Rank) extends HandValue
case class ThreeOfAKind(rank: Rank) extends HandValue
case class FullHouse(threeRank: Rank, pairRank: Rank) extends HandValue
case class Flush(rank: Rank) extends HandValue
case class HighCard(highest: Int, second: Int) extends HandValue

object HandValue{

  def getValue(hand: Hand, table: List[Card]): HandValue = {
    val allCards = table :+ hand.card1 :+ hand.card2

    val flush = getFlush(allCards)

    if(flush.isDefined){
      Flush(flush.get)
    }else {

      val threeOfAKind = getThreeOfAKind(allCards)
      val cardsWithThreeOfAKindsRemoved = threeOfAKind.map(x => allCards.filterNot(_.rank == x)).getOrElse(allCards)
      val twoOfAKinds = getTwoOfAKinds(cardsWithThreeOfAKindsRemoved)

      (twoOfAKinds, threeOfAKind) match {
        case (List(pair1, pair2), None) => TwoPair(pair1, pair2)
        case (List(pair), Some(threeOfAKind)) => FullHouse(threeOfAKind, pair)
        case (List(pair), None) => Pair(pair)
        case (List(), Some(threeOfAKind)) => ThreeOfAKind(threeOfAKind)
        case (List(), None) => HighCard(hand.card1.rank, hand.card2.rank)
        case unmatched => {
          println("unmatched = " + unmatched)
          throw new IllegalStateException()
        }
      }
    }
  }

  private def getThreeOfAKind(cards: List[Card]): Option[Rank] = groupCardsByRank(cards)
    .collect{case (rank, List(card1, card2, card3, _*))=> rank}
    .headOption

  private def getTwoOfAKinds(cards: List[Card]): List[Rank] = groupCardsByRank(cards)
    .collect { case (rank, List(card1, card2, _*)) => rank}
    .toList
    .sortBy(r => -Rank.toInt(r))

  private def getFlush(cards: List[Card]) = cards.groupBy(_.suit)
    .collect{case (suit, List(card1, card2, card3, card4, card5)) => List(card1, card2, card3, card4, card5)}
    .headOption
    .map(x=> x.sortBy(r=> -Rank.toInt(r.rank)).head.rank)



  private def groupCardsByRank(cards: List[Card]) = cards.groupBy(_.rank)
}