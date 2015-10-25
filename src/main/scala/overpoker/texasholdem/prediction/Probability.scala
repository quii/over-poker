package overpoker.texasholdem.prediction

import overpoker.playingcards._
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

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) / 1000000000.0 + "s")
    result
  }

  def main (args: Array[String]){
    println(time { probabilities(3)(PlayerHand(Ace of Spades, King of Spades)) })
    println(time { probabilities(4)(PlayerHand(Ace of Spades, King of Spades)) })
    //time { println(probabilities(5)(PlayerHand(Ace of Spades, King of Spades))) }
  }
}

//Map(AnyStraight -> 0.0032142857142857142, AnyHighCard -> 0.5271428571428571, AnyThreeOfAKind -> 0.015714285714285715, AnyFourOfAKind -> 1.0204081632653062E-4, AnyPair -> 0.40408163265306124, AnyStraightFlush -> 0.0, AnyRoyalFlush -> 5.102040816326531E-5, AnyFullHouse -> 9.183673469387755E-4, AnyFlush -> 0.00836734693877551, AnyTwoPair -> 0.04040816326530612)
//Map(AnyStraight -> 0.010720798957881025, AnyHighCard -> 0.3387016934433348, AnyThreeOfAKind -> 0.03060790273556231, AnyFourOfAKind -> 4.559270516717325E-4, AnyPair -> 0.47206686930091185, AnyStraightFlush -> 1.1289622231871472E-4, AnyRoyalFlush -> 4.211897524967434E-4, AnyFullHouse -> 0.006304819800260529, AnyFlush -> 0.028840642640034737, AnyTwoPair -> 0.11176726009552758)
//Map(AnyStraight -> 0.02410938473446733, AnyHighCard -> 0.18224338764182824, AnyThreeOfAKind -> 0.04388179878797032, AnyFourOfAKind -> 0.0012592270950933565, AnyPair -> 0.45506192301157283, AnyStraightFlush -> 0.001087900470086277, AnyRoyalFlush -> 0.0014598161188619759, AnyFullHouse -> 0.02192980800090619, AnyFlush -> 0.06327285770922615, AnyTwoPair -> 0.20569389642998734)