package model.texasholdem.probability.prediction

import org.scalatest.FunSpec
import overpoker.playingcards._
import overpoker.playingcards.Rank._
import overpoker.texasholdem.prediction.Probability.Probabilities
import overpoker.texasholdem.prediction._
import org.scalatest.Matchers._

class ProbabilityEngineTest extends FunSpec {

  describe("Before the flop") {
    describe("Probabilities on the flop") {
      describe("Probability for a Pair") {
        it("contains the correct probability - player hand not pair") {
          val playerHand = PlayerHand(6 of Clubs, King of Diamonds)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyPair) should be(0.40408163265306124)
        }

        it("contains the correct probability - player hand is pair") {
          val playerHand = PlayerHand(10 of Spades, 10 of Hearts)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyPair) should be(0.7183673469387755)
        }
      }

      describe("Probability for Two Pair") {
        it("contains the correct probability for Two Pair -  player hand not pair") {
          val playerHand = PlayerHand(King of Hearts, Ace of Hearts)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyTwoPair) should be(0.04040816326530612)
        }

        it("contains the correct probability for Two Pair - player hand is pair") {
          val playerHand = PlayerHand(2 of Clubs, 2 of Spades)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyTwoPair) should be(0.16163265306122448)
        }
      }

      describe("Three of a kind") {
        it("contains the correct probability for Three of a Kind - player hand not pair") {
          val playerHand = PlayerHand(5 of Diamonds, 6 of Diamonds)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyThreeOfAKind) should be(0.015714285714285715)
        }

        it("contains the correct probability for Three of a Kind - player hand pair") {
          val playerHand = PlayerHand(5 of Diamonds, 5 of Clubs)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyThreeOfAKind) should be(0.10775510204081633)
        }
      }

      describe("Straight") {
        it("contains the correct probability for a Straight - player hand pair") {
          val playerHand = PlayerHand(Ace of Spades, Ace of Clubs)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyStraight) should be(0.0)
        }

        it("contains the correct probability for a Straight - player hand too far apart") {
          val playerHand = PlayerHand(7 of Clubs, Ace of Clubs)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyStraight) should be(0.0)
        }

        it("contains the correct probability for a Straight - player hand: can't make RoyalFlush, can't make flush, can't make straight flush, isn't pair, is in middle of pack by rank"){
          val playerHand = PlayerHand(7 of Diamonds, 8 of Clubs)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyStraight) should be(0.013061224489795919)
        }

        it("contains the correct probability for a Straight - player hand: can't make RoyalFlush, can't make flush, can make straight flush, isn't pair, is in middle of pack by rank"){
          val playerHand = PlayerHand(6 of Diamonds, 7 of Diamonds)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyStraight) should be(0.012857142857142857)
        }
      }

      describe("rest") {
        it("has non zero values") {
          val playerHand = PlayerHand(Ace of Spades, King of Spades)
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyFlush) should be > 0.0
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyFullHouse) should be > 0.0
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyFourOfAKind) should be > 0.0
          ProbabilityEngine.probabilitiesOnFlop(playerHand)(AnyRoyalFlush) should be > 0.0

          val playerHand2 = PlayerHand(5 of Spades, 6 of Spades)
          ProbabilityEngine.probabilitiesOnFlop(playerHand2)(AnyStraightFlush) should be > 0.0
        }
      }

    }

    describe("Probabilities on the turn") {
      it("contains values for all hands"){
        val playerHand = PlayerHand(Ace of Spades, King of Spades)
        val probabilities: Probabilities = ProbabilityEngine.probabilitiesOnTurn(playerHand)
        probabilities(AnyHighCard) should be > 0.0
        probabilities(AnyPair) should be > 0.0
        probabilities(AnyTwoPair) should be > 0.0
        probabilities(AnyThreeOfAKind) should be > 0.0
        probabilities(AnyStraight) should be > 0.0
        probabilities(AnyFlush) should be > 0.0
        probabilities(AnyFullHouse) should be > 0.0
        probabilities(AnyFourOfAKind) should be > 0.0
        probabilities(AnyRoyalFlush) should be > 0.0

        ProbabilityEngine.probabilitiesOnTurn(PlayerHand(2 of Hearts, 3 of Hearts))(AnyStraightFlush) should be > 0.0
      }
    }

    describe("Probabilities on the river") {
      it("contains values for all hands"){
        val playerHand = PlayerHand(Ace of Spades, King of Spades)
        val probabilities: Probabilities = ProbabilityEngine.probabilitiesOnRiver(playerHand)
        probabilities(AnyHighCard) should be > 0.0
        probabilities(AnyPair) should be > 0.0
        probabilities(AnyTwoPair) should be > 0.0
        probabilities(AnyThreeOfAKind) should be > 0.0
        probabilities(AnyStraight) should be > 0.0
        probabilities(AnyFlush) should be > 0.0
        probabilities(AnyFullHouse) should be > 0.0
        probabilities(AnyFourOfAKind) should be > 0.0
        probabilities(AnyRoyalFlush) should be > 0.0

        ProbabilityEngine.probabilitiesOnRiver(PlayerHand(2 of Hearts, 3 of Hearts))(AnyStraightFlush) should be > 0.0
      }
    }

  }

}
