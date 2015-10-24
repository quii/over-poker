package overpoker.texasholdem.hands

import overpoker.playingcards._
import OrganisedDeck._

sealed trait Hand

case class Pair(rank: Rank) extends Hand
case class TwoPair(highest: Rank, lowest: Rank) extends Hand
case class ThreeOfAKind(rank: Rank) extends Hand
case class Straight(rank: Rank) extends Hand
case class Flush(rank: Rank) extends Hand
case class FullHouse(threeRank: Rank, pairRank: Rank) extends Hand
case class FourOfAKind(rank: Rank) extends Hand
case class StraightFlush(rank: Rank) extends Hand
case object RoyalFlush extends Hand
case class HighCard(highest: Rank, second: Rank) extends Hand

object Pair{
  def apply(cards: Vector[Card]): Option[Pair] =
    cards.groupByRank.toVector
      .filterNot(_.count < 2)
      .sortBy(_.rank.toInt) //todo: Make an implicit ordering or whatever it is
      .headOption.map(highestRankedPair => Pair(highestRankedPair.rank))
}

object TwoPair{
   def apply(cards: Vector[Card]): Option[TwoPair] = {
    val pairs: Vector[RankCount] = cards.groupByRank.toVector
      .filterNot(_.count < 2)
      .sortBy(-_.rank.toInt)
    pairs match{
      case Vector(RankCount(highestRank, _), RankCount(lowestRank, _)) => Some(TwoPair(highestRank, lowestRank))
      case _ => None
    }
  }
}

object ThreeOfAKind{
  def apply(cards: Vector[Card]): Option[ThreeOfAKind] =
    cards.groupByRank.toVector
    .filterNot(_.count<3)
    .sortBy(_.rank.toInt)
    .headOption.map(highestRanked => ThreeOfAKind(highestRanked.rank))
}

object Straight{
  def apply(cards: Vector[Card]): Option[Straight] = {
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
}

object Flush{
  def apply(cards: Vector[Card]): Option[Flush] = {
    cards.groupBySuit.toVector
    .filterNot(_.ranks.size<5)
    .headOption.map(flush => Flush(flush.ranks.sortBy(-_.toInt).head)) //todo: Not really finding highest ranking flush here..
  }
}

object FullHouse{
  def apply(cards: Vector[Card]): Option[FullHouse] = {
    def groupCardsByRank(cards: Vector[Card]) = cards.groupBy(_.rank)
    val ranked = groupCardsByRank(cards)
    val cardsInThreeOfAKind = ranked.collect{case (rank, Vector(card1, card2, card3)) => card1}

    cardsInThreeOfAKind.headOption.flatMap{ threeOfAKind =>
      val threeOfAKindRank = threeOfAKind.rank
      val cardsWithoutTheThrees = cards.filterNot(x=> x.rank==threeOfAKindRank)
      val twoOfAKinds = groupCardsByRank(cardsWithoutTheThrees).collect{case (rank, Vector(card1, card2)) => rank}
      twoOfAKinds.headOption.map(twoRank=> FullHouse(threeOfAKindRank, twoRank))
    }
  }
}

object FourOfAKind{
  def apply(cards: Vector[Card]): Option[FourOfAKind] =
    cards.groupByRank.toVector
      .filterNot(_.count < 4)
      .sortBy(_.rank.toInt)
      .headOption.map(highestRankedPair => FourOfAKind(highestRankedPair.rank))
}

object Hand{

  def getValues(hand: PlayerHand, communityCards: Vector[Card]): Vector[Hand] = {
    val allCards = communityCards :+ hand.card1 :+ hand.card2

    val flush = Flush(allCards)
    val fourOfAKind = FourOfAKind(allCards)
    val threeOfAKind = ThreeOfAKind(allCards)
    val straight = Straight(allCards)
    val pair = Pair(allCards)
    val twoPair = TwoPair(allCards)
    val fullHouse = FullHouse(allCards)

    val straightFlush = if(straight.isDefined && flush.isDefined) Some(StraightFlush(straight.get.rank)) else None
    val royalFlush = if(straightFlush.isDefined && straightFlush.get.rank==Ace) Some(RoyalFlush) else None

    val highCard = Deck(Vector(hand.card1, hand.card2)).sortedByRank

    Vector(royalFlush, straightFlush, fourOfAKind, fullHouse, flush, straight, threeOfAKind, twoPair, pair)
      .flatten :+ HighCard(highCard(0).rank, highCard(1).rank)
  }

}