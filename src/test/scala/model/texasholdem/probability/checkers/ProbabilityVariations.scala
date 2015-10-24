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
//      .par.foreach{ hand =>
//        println(hand + " " + probs.par.map(_(hand)).distinct.length)
//      }
      .foreach{ hand =>
      println(hand + " " + probs.par.map(_(hand)).distinct)
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



// Royal Flush      ParVector(0.0, 5.102040816326531E-5)
// Straight Flush   ParVector(1.0204081632653062E-4, 5.102040816326531E-5, 0.0, 1.530612244897959E-4, 2.0408163265306123E-4)
// Four of a Kind   ParVector(1.0204081632653062E-4, 0.0024489795918367346)
// Full House       ParVector(9.183673469387755E-4, 0.009795918367346938)
// Flush            ParVector(0.008316326530612246, 0.00836734693877551, 0.008418367346938776, 0.0, 0.00826530612244898, 0.008214285714285714)
// Straight         ParVector(0.0064285714285714285, 0.0032142857142857142, 0.0, 0.006530612244897959, 0.0032653061224489797, 0.009642857142857142, 0.009795918367346938, 0.012857142857142857, 0.013061224489795919)
// Three of a Kind  ParVector(0.015714285714285715, 0.10775510204081633)
// Two Pair         ParVector(0.04040816326530612, 0.16163265306122448)
// Pair             ParVector(0.40408163265306124, 0.7183673469387755)
// High Card        ParVector(0.5239285714285714, 0.5271428571428571, 0.5303571428571429, 0.0, 0.5322448979591837, 0.5355102040816326, 0.5387755102040817, 0.5207142857142857, 0.5289795918367347, 0.5175, 0.5257142857142857)
