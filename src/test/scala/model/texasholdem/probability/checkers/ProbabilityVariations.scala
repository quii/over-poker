package model.texasholdem.probability.checkers

import overpoker.playingcards.{Card, PlayerHand, Deck}
import overpoker.texasholdem.hands._

object ProbabilityVariations {


  def main(args: Array[String]) {
    val allPlayerHands: List[PlayerHand] = Deck.fullDeck.cards.combinations(2).toList.map(cards => PlayerHand(cards.head, cards(1)))

    // Before the flop, what's the probability of X after the flop
    def probabilities(playerHand: PlayerHand): Map[String, Double] = {
      val deck: Vector[Card] = Deck.fullDeck.cards.filter(card => card != playerHand.card1 && card != playerHand.card2)
      val allFlopCombinations: Vector[Vector[Card]] = deck.combinations(3).toVector

      val hands: Vector[Hand] = allFlopCombinations.map(flop => Hand.getValues(playerHand, flop).head)
      val numFlopVariations: Double = allFlopCombinations.length.toDouble
        Map(
          "High Card" -> hands.count(hand => hand.isInstanceOf[HighCard]).toDouble / numFlopVariations,
          "Pair" -> hands.count(hand => hand.isInstanceOf[Pair]).toDouble / numFlopVariations,
          "Two Pair" -> hands.count(hand => hand.isInstanceOf[TwoPair]).toDouble / numFlopVariations,
          "Three of a Kind" -> hands.count(hand => hand.isInstanceOf[ThreeOfAKind]).toDouble / numFlopVariations,
          "Straight" -> hands.count(hand => hand.isInstanceOf[Straight]).toDouble / numFlopVariations,
          "Flush" -> hands.count(hand => hand.isInstanceOf[Flush]).toDouble / numFlopVariations,
          "Full House" -> hands.count(hand => hand.isInstanceOf[FullHouse]).toDouble / numFlopVariations,
          "Four of a Kind" -> hands.count(hand => hand.isInstanceOf[FourOfAKind]).toDouble / numFlopVariations,
          "Straight Flush" -> hands.count(hand => hand.isInstanceOf[StraightFlush]).toDouble / numFlopVariations,
          "Royal Flush" -> hands.count(hand => hand == RoyalFlush).toDouble / numFlopVariations
        )
    }

    val probs: List[Map[String, Double]] = allPlayerHands.par.map(probabilities).toList

    List("High Card", "Pair", "Two Pair", "Three of a Kind", "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush", "Royal Flush")
      .par.foreach{ hand =>
        println(hand + " " + probs.par.map(_(hand)).distinct.length)
      }
  }
}

// Hand            Num different Probabilities
// Royal Flush          2
// Straight Flush       5
// Four of a Kind       2
// Full House           2
// Flush                6
// Straight             9
// Three of a Kind      2
// Two Pair             2
// Pair                 2
// High Card            11
