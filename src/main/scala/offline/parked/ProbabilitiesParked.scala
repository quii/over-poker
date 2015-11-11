package offline.parked

import overpoker.playingcards.Rank._
import overpoker.playingcards._
import overpoker.texasholdem.hands._
import prediction._

import scala.language.implicitConversions

case class ProbabilitiesParked(playerHand: PlayerHand, flop: HandProbabilities) {
  override def toString = {
    s"""PlayerHand: ${playerHand.toString}
       |${flop.toString}""".stripMargin
  }
}

object ProbabilitiesParked {

  def preFlop: Vector[ProbabilitiesParked] = {
    val playerHands = Deck.fullDeck.cards.combinations(2).map(cards => PlayerHand(cards.head, cards(1))).toVector
    playerHands.par.map(probabilities).toVector
  }

  def probabilities(playerHand: PlayerHand): ProbabilitiesParked = {
    ProbabilitiesParked(playerHand, flop = flopProbabilities(playerHand))
  }

  def flopProbabilities(playerHand: PlayerHand): HandProbabilities = {
    val allFlops = Deck.fullDeck.cards.filter(card => card != playerHand.card1 && card != playerHand.card2).combinations(3).toVector
    val allPlayerOnlyHands: Vector[Hand] = allFlops.map(flop => Hand.playersBestHand(playerHand, flop))
    val allProbabilities = allPlayerOnlyHands.groupBy(HandType(_)).mapValues(_.size.toDouble / allFlops.length.toDouble)
    HandProbabilities(
      royalFlush = allProbabilities.getOrElse(AnyRoyalFlush, 0.0),
      straightFlush = allProbabilities.getOrElse(AnyStraightFlush, 0.0),
      fourOfAKind = allProbabilities.getOrElse(AnyFourOfAKind, 0.0),
      fullHouse = allProbabilities.getOrElse(AnyFullHouse, 0.0),
      flush = allProbabilities.getOrElse(AnyFlush, 0.0),
      straight = allProbabilities.getOrElse(AnyStraight, 0.0),
      threeOfAKind = allProbabilities.getOrElse(AnyThreeOfAKind, 0.0),
      twoPair = allProbabilities.getOrElse(AnyTwoPair, 0.0),
      pair = allProbabilities.getOrElse(AnyPair, 0.0),
      highCard = allProbabilities.getOrElse(AnyHighCard, 0.0),
      nothing = allProbabilities.getOrElse(NothingHand, 0.0))
  }

  def time[R](block: => R): R = {
    val start = System.nanoTime()
    val returnVal = block
    val timeTaken = System.nanoTime() - start
    println("Time taken: " + timeTaken / 1000000000.0 + "s")
    returnVal
  }

  def main(args: Array[String]) {
//    time{ probabilities(PlayerHand(3 of Clubs, 3 of Spades)) }
    time{ preFlop }
  }
}


object ProbabilitiesTwo {

  def probability(numToChoose: Int)(playerHand: PlayerHand, deck: Deck): HandProbabilities = {
    implicit def toVector(playerHand: PlayerHand): Vector[Card] = Vector(playerHand.card1, playerHand.card2)

    val allProbabilities = deck.cards
      .filterNot(playerHand.contains(_))
      .combinations(numToChoose)
      .map(Hand.playersBestHand(playerHand, _))
      .toVector.groupBy(HandType(_))
      .mapValues(_.length.toDouble / choose(deck.cards.length - 2, numToChoose).toDouble)

    HandProbabilities(
      royalFlush = allProbabilities.getOrElse(AnyRoyalFlush, 0.0),
      straightFlush = allProbabilities.getOrElse(AnyStraightFlush, 0.0),
      fourOfAKind = allProbabilities.getOrElse(AnyFourOfAKind, 0.0),
      fullHouse = allProbabilities.getOrElse(AnyFullHouse, 0.0),
      flush = allProbabilities.getOrElse(AnyFlush, 0.0),
      straight = allProbabilities.getOrElse(AnyStraight, 0.0),
      threeOfAKind = allProbabilities.getOrElse(AnyThreeOfAKind, 0.0),
      twoPair = allProbabilities.getOrElse(AnyTwoPair, 0.0),
      pair = allProbabilities.getOrElse(AnyPair, 0.0),
      highCard = allProbabilities.getOrElse(AnyHighCard, 0.0),
      nothing = allProbabilities.getOrElse(NothingHand, 0.0))
  }

  def choose(n: Int, r: Int): BigInt = {
    factorial(n) / (factorial(n - r) * factorial(r))
  }

  def factorial(n: BigInt): BigInt = {
    (BigInt(1) to n).foldLeft(BigInt(1))(_ * _)
  }

  def main(args: Array[String]) {
    val playerHand: PlayerHand = PlayerHand(3 of Clubs, 3 of Spades)
    println(probability(3)(playerHand, Deck.fullDeck) == ProbabilitiesParked.flopProbabilities(playerHand))
  }
}

case class HandProbabilities(
                              royalFlush: Double = -100.0,
                              straightFlush: Double = -100.0,
                              fourOfAKind: Double = -100.0,
                              fullHouse: Double = -100.0,
                              flush: Double = -100.0,
                              straight: Double = -100.0,
                              threeOfAKind: Double = -100.0,
                              twoPair: Double = -100.0,
                              pair: Double = -100.0,
                              highCard: Double = -100.0,
                              nothing: Double = -100.0) {
  override def toString = {
    s"""Royal Flush:  $royalFlush
       |Straight Flush: $straightFlush
       |Four of a Kind: $fourOfAKind
       |Flush: $flush
       |Straight: $straight
       |Three of a Kind: $threeOfAKind
       |Two Pair: $twoPair
       |Pair: $pair
       |High Card: $highCard
       |Nothing: $nothing
     """.stripMargin
  }
}

