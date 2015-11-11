package prediction

import overpoker.playingcards.{Card, Deck, PlayerHand}
import overpoker.texasholdem.hands._

import scala.language.implicitConversions

object Probability {

//  def preFlop_flop(numberOfCardsInDeck: Int): Map[PlayerHand, Map[HandType, Double]] = ???

//  def preFlop_turn(numberOfCardsInDeck: Int): Any = "stubbed"

//  def preFlop_river(numberOfCardsInDeck: Int): Any = "stubbed"

  implicit def vectorToPlayerHand(playerCards: Vector[Card]): PlayerHand = if (playerCards.length == 2) PlayerHand(card1 = playerCards(0), card2 = playerCards(1)) else throw new IllegalArgumentException

  def probability(playerCards: Vector[Card], deck: Vector[Card], numChoose: Int): Map[HandType, Double] = {
    val allCombinations = deck.filterNot(playerCards.contains(_)).combinations(numChoose).toVector
    val handCounts = allCombinations
      .map(combination => Hand.playersBestHand(playerCards, combination))
      .groupBy(HandType(_))
      .mapValues(_.length)

    Map(
      AnyRoyalFlush -> handCounts.getOrElse(AnyRoyalFlush, 0).toDouble / allCombinations.length
    )
  }

}

sealed trait HandType

case object AnyRoyalFlush extends HandType
case object AnyStraightFlush extends HandType
case object AnyFourOfAKind extends HandType
case object AnyFullHouse extends HandType
case object AnyFlush extends HandType
case object AnyStraight extends HandType
case object AnyThreeOfAKind extends HandType
case object AnyTwoPair extends HandType
case object AnyPair extends HandType
case object AnyHighCard extends HandType
case object NothingHand extends HandType

object HandType {
  def apply(hand: Hand) = hand match {
    case Pair(_) => AnyPair
    case TwoPair(_, _) => AnyTwoPair
    case ThreeOfAKind(_) => AnyThreeOfAKind
    case Straight(_) => AnyStraight
    case Flush(_) => AnyFlush
    case FullHouse(_, _) => AnyFullHouse
    case FourOfAKind(_) => AnyFourOfAKind
    case StraightFlush(_) => AnyStraightFlush
    case RoyalFlush => AnyRoyalFlush
    case HighCard(_) => AnyHighCard
    case _ => NothingHand
  }
}