package overpoker.model

sealed trait HandValue

case class Pair(rank: Rank) extends HandValue
case class TwoPair(highest: Rank, lowest: Rank) extends HandValue
case class ThreeOfAKind(rank: Rank) extends HandValue
case class Straight(rank: Rank) extends HandValue
case class Flush(rank: Rank) extends HandValue
case class FullHouse(threeRank: Rank, pairRank: Rank) extends HandValue
case class FourOfAKind(rank: Rank) extends HandValue
case class StraightFlush(rank: Rank) extends HandValue
case object RoyalFlush extends HandValue
case class HighCard(highest: Int, second: Int) extends HandValue

object HandValue{

  def getValues(hand: Hand, table: Vector[Card]): Vector[HandValue] = {
    val allCards = table :+ hand.card1 :+ hand.card2

    val flush = getFlush(allCards).map(Flush)
    val fourOfAKind = getFourOfAKind(allCards).map(FourOfAKind)
    val threeOfAKind = getThreeOfAKind(allCards).map(ThreeOfAKind)
    val straight = getStraight(allCards)
    val straightFlush = if(straight.isDefined && flush.isDefined) Some(StraightFlush(straight.get.rank)) else None
    val royalFlush = if(straightFlush.isDefined && straightFlush.get.rank==Ace) Some(RoyalFlush) else None

    val cardsWithThreeOfAKindsRemoved = threeOfAKind.map(t=> allCards.filterNot(_.rank==t.rank)).getOrElse(allCards)

    val twoOfAKinds = getTwoOfAKinds(cardsWithThreeOfAKindsRemoved) match{
      case Vector(pair1, pair2) => Some(TwoPair(pair1, pair2))
      case Vector(pair1) => Some(Pair(pair1))
      case Vector() => None
    }

    val fullHouse = (twoOfAKinds, threeOfAKind) match{
      case (Some(Pair(p)), Some(ThreeOfAKind(t))) => Some(FullHouse(t, p))
      case (Some(TwoPair(p, _)), Some(ThreeOfAKind(t))) => Some(FullHouse(t, p))
      case _ => None
    }

    val highCard = Deck(Vector(hand.card1, hand.card2)).sortedByRank

    Vector(flush, fourOfAKind, threeOfAKind, twoOfAKinds, fullHouse, straight, straightFlush, royalFlush)
      .flatten :+ HighCard(highCard(0).rank, highCard(1).rank)
  }

  private def getFourOfAKind(cards: Vector[Card]): Option[Rank] = groupCardsByRank(cards)
    .collect{case (rank, Vector(card1, card2, card3, card4, _*))=> rank}
    .headOption

  private def getThreeOfAKind(cards: Vector[Card]): Option[Rank] = groupCardsByRank(cards)
    .collect{case (rank, Vector(card1, card2, card3, _*))=> rank}
    .headOption

  private def getTwoOfAKinds(cards: Vector[Card]): Vector[Rank] = groupCardsByRank(cards)
    .collect { case (rank, Vector(card1, card2, _*)) => rank}
    .toVector
    .sortBy(r => -Rank.toInt(r))

  private def getFlush(cards: Vector[Card]) = cards.groupBy(_.suit)
    .collect{case (suit, Vector(card1, card2, card3, card4, card5)) => Vector(card1, card2, card3, card4, card5)}
    .headOption
    .map(x=> x.sortBy(r=> -Rank.toInt(r.rank)).head.rank)

  private def getStraight(cards: Vector[Card]): Option[Straight] = {

    val cardsWithAceLowAdded = {
      val ace = cards.find(c=> c.rank==Ace)
      ace.map(a=> cards :+ a.copy(rank = AceLow)).getOrElse(cards)
    }

    val sorted = Deck(cardsWithAceLowAdded.toVector).sortedByRank.reverse

    val straightAttempt = sorted.foldLeft(Vector[Rank]())((straight, card) =>{
      straight.reverse match{
        case Vector(card1, card2, card3, card4, card5) => straight
        case head +: tail if card.rank - head == 1 => straight :+ card.rank
        case _ => Vector(card.rank)
      }
    })

    if(straightAttempt.size==5) Some(Straight(straightAttempt.reverse.head)) else None
  }

  private def groupCardsByRank(cards: Vector[Card]) = cards.groupBy(_.rank)

}