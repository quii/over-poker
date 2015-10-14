package model.texasholdem.probability.prediction

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.playingcards.Rank._
import overpoker.playingcards._
import overpoker.texasholdem.prediction.Probability

class TestPrediction extends FunSpec {

  describe("Before the flop"){

    it("returns the probability of a pair on the flop as 1 given two cards that already make a pair") {
      Probability.ofPair(pairHand, communityCardsBeforeFlop).flop should be(1.0)
    }

    it("returns the probability of a pair on the turn as 1 given two cards that already make a pair") {
      Probability.ofPair(pairHand, communityCardsBeforeFlop).turn should be(1.0)
    }

    it("returns the probability of a pair on the river as 1 given two cards that already make a pair") {
      Probability.ofPair(pairHand, communityCardsBeforeFlop).river should be(1.0)
    }

    it("returns the probability of a pair on the flop given two cards that don't already make a pair")(pending)

  }

  private val pairHand = PlayerHand(10 of Spades, 10 of Hearts)
  private val communityCardsBeforeFlop = Vector.empty

}
