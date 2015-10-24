package overpoker.texasholdem.prediction

import overpoker.playingcards.{Card, Deck, PlayerHand}
import overpoker.texasholdem.hands._
import overpoker.texasholdem.prediction.Probability.Probabilities

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

object HandType {
  def apply(hand: Hand) = hand match {
    case Pair(_) => AnyPair
    case TwoPair(_,_) => AnyTwoPair
    case ThreeOfAKind(_) => AnyThreeOfAKind
    case Straight(_) => AnyStraight
    case Flush(_) => AnyFlush
    case FullHouse(_,_) => AnyFullHouse
    case FourOfAKind(_) => AnyFourOfAKind
    case StraightFlush(_) => AnyStraightFlush
    case RoyalFlush => AnyRoyalFlush
    case _ => AnyHighCard
  }
}

case class ProbabilityModel(flop: Option[Probabilities] = None, turn: Option[Probabilities] = None, river: Option[Probabilities] = None)
case class ProbabilityError(message: String = "")

object Probability {
  type Probabilities = Map[HandType, Double]

  def probabilities(playerHand: PlayerHand, communityCards: Vector[Card]): Either[ProbabilityError, ProbabilityModel] = communityCards.length match {
    case 0 => Right(ProbabilityModel(flop = Some(Map.empty), turn = Some(Map.empty), river = Some(Map.empty)))
    case 3 => Right(ProbabilityModel(turn = Some(Map.empty), river = Some(Map.empty)))
    case 4 => Right(ProbabilityModel(river = Some(Map.empty)))
    case 5 => Right(ProbabilityModel())
    case _ => Left(ProbabilityError(message = "Wrong number of community cards..."))
  }
}

object ProbabilityEngine {
  def probabilitiesOnRiver = probabilities(5) _

  def probabilitiesOnFlop = probabilities(3) _

  def probabilitiesOnTurn = probabilities(4) _


  private def probabilities(choose: Int)(playerHand: PlayerHand): Probabilities = {
    val deck: Vector[Card] = Deck.fullDeck.cards.filter(card => card != playerHand.card1 && card != playerHand.card2)

    val allCombinations = deck.combinations(choose).toVector
    val allCombinationsOfPlayerHandAndChosenCards: Vector[Vector[Hand]] = allCombinations.par.map(chosenCards => Hand.getValues(playerHand, chosenCards)).toVector

    val numberOfCombinations: Double = allCombinationsOfPlayerHandAndChosenCards.length.toDouble

    def probability(hand: HandType) = allCombinationsOfPlayerHandAndChosenCards.par.count(combination => HandType(combination.head) == hand).toDouble / numberOfCombinations

    Map(
      AnyHighCard -> probability(AnyHighCard),
      AnyPair -> probability(AnyPair),
      AnyTwoPair -> probability(AnyTwoPair),
      AnyThreeOfAKind -> probability(AnyThreeOfAKind),
      AnyStraight -> probability(AnyStraight),
      AnyFlush -> probability(AnyFlush),
      AnyFullHouse -> probability(AnyFullHouse),
      AnyFourOfAKind -> probability(AnyFourOfAKind),
      AnyStraightFlush -> probability(AnyStraightFlush),
      AnyRoyalFlush -> probability(AnyRoyalFlush)
    )
  }

}