package overpoker.model


sealed trait HandValue

case class Pair(rank: Rank) extends HandValue
case class TwoPair(highest: Rank, lowest: Rank) extends HandValue
case class ThreeOfAKind(rank: Rank) extends HandValue
case class Straight(rank: Rank) extends HandValue
case class Flush(rank: Rank) extends HandValue
case class FullHouse(threeRank: Rank, pairRank: Rank) extends HandValue
case class FourOfAKind(rank: Rank) extends HandValue
case class HighCard(highest: Int, second: Int) extends HandValue

object HandValue{

  def getValues(hand: Hand, table: List[Card]): List[HandValue] = {
    val allCards = table :+ hand.card1 :+ hand.card2

    val flush = getFlush(allCards).map(Flush)
    val fourOfAKind = getFourOfAKind(allCards).map(FourOfAKind)
    val threeOfAKind = getThreeOfAKind(allCards).map(ThreeOfAKind)

    val cardsWithThreeOfAKindsRemoved = threeOfAKind.map(t=> allCards.filterNot(_.rank==t.rank)).getOrElse(allCards)

    val twoOfAKinds = getTwoOfAKinds(cardsWithThreeOfAKindsRemoved) match{
      case List(pair1, pair2) => Some(TwoPair(pair1, pair2))
      case List(pair1) => Some(Pair(pair1))
      case Nil => None
    }

    val fullHouse = (twoOfAKinds, threeOfAKind) match{
      case (Some(Pair(p)), Some(ThreeOfAKind(t))) => Some(FullHouse(t, p))
      case (Some(TwoPair(p, _)), Some(ThreeOfAKind(t))) => Some(FullHouse(t, p))
      case _ => None
    }

    val highCard = List(hand.card1, hand.card2).sortBy(c => -Rank.toInt(c.rank))

    List(flush, fourOfAKind, threeOfAKind, twoOfAKinds, fullHouse, getStraight(allCards))
      .flatten :+ HighCard(highCard(0).rank, highCard(1).rank)
  }

  private def getFourOfAKind(cards: List[Card]): Option[Rank] = groupCardsByRank(cards)
    .collect{case (rank, List(card1, card2, card3, card4, _*))=> rank}
    .headOption

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

  private def getStraight(cards: List[Card]): Option[Straight] = {

    val cardsWithAceLowAdded = {
      val ace = cards.find(c=> c.rank==Ace)
      ace.map(a=> cards :+ a.copy(rank = AceLow)).getOrElse(cards)
    }

    val sorted = sortCards(cardsWithAceLowAdded).reverse

    val straightAttempt = sorted.foldLeft(List[Rank]())((straight, card) =>{
      straight.reverse match{
        case List(card1, card2, card3, card4, card5) => straight
        case head :: tail if card.rank - head == 1 => straight :+ card.rank
        case _ => List(card.rank)
      }
    })

    if(straightAttempt.size==5) Some(Straight(straightAttempt.reverse.head)) else None
  }

  private def groupCardsByRank(cards: List[Card]) = cards.groupBy(_.rank)

  private def sortCards(cards: List[Card]) = cards.sortBy(r=> -Rank.toInt(r.rank))
}