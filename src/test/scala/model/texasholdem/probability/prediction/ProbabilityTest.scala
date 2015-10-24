package model.texasholdem.probability.prediction

import org.scalatest.FunSpec
import overpoker.playingcards._
import overpoker.texasholdem.prediction.{ProbabilityModel, Probability}
import overpoker.playingcards.Rank._
import org.scalatest.Matchers._

class ProbabilityTest extends FunSpec {

  describe("The probability interface") {

    describe("Before the flop") {
      it("returns a model with probabilities for the flop") {
        probabilitiesBeforeTheFlop.flop should be(defined)
      }

      it("returns a model with probabilities for the turn") {
        probabilitiesBeforeTheFlop.turn should be(defined)
      }

      it("returns a model with probabilities for the river") {
        probabilitiesBeforeTheFlop.river should be(defined)
      }
    }

    describe("After the Flop, before the Turn") {
      it("returns no probabilities for the flop") {
        probabilitiesAfterTheFlop.flop should be(None)
      }

      it("returns probabilities for the turn") {
        probabilitiesAfterTheFlop.turn should be(defined)
      }

      it("returns probabilities for the river") {
        probabilitiesAfterTheFlop.river should be(defined)
      }
    }

    describe("After the Turn, before the river") {
      it("returns no probabilities for the flop") {
        probabilitiesAfterTurnBeforeRiver.flop should be(None)
      }

      it("returns no probabilities for the turn") {
        probabilitiesAfterTurnBeforeRiver.turn should be(None)
      }

      it("returns probabilities for the river") {
        probabilitiesAfterTurnBeforeRiver.river should be(defined)
      }
    }

    describe("After the River") {
      it("returns no probabilities") {
        probabilitiesAfterTheRiver.flop should be(None)
        probabilitiesAfterTheRiver.turn should be(None)
        probabilitiesAfterTheRiver.river should be(None)
      }
    }


    describe("Unhappy path") {
      it("returns an error message when 2 community cards are provided") {
        Probability.probabilities(playerHand, Vector(4 of Clubs, 5 of Hearts)).left.get.message should be("Wrong number of community cards...")
      }

      it("returns and error message when 6 cards are provided") {
        Probability.probabilities(playerHand, Vector(4 of Clubs, 5 of Hearts)).left.get.message should be("Wrong number of community cards...")
      }
    }

  }

  private val playerHand = PlayerHand(2 of Spades, 3 of Diamonds)
  private val zeroCommunityCards = Vector.empty
  private val probabilitiesBeforeTheFlop: ProbabilityModel = Probability.probabilities(playerHand, zeroCommunityCards).right.get

  private val threeCommunityCards = Vector(4 of Clubs, 5 of Hearts, 6 of Spades)
  private val probabilitiesAfterTheFlop = Probability.probabilities(playerHand, threeCommunityCards).right.get

  private val fourCommunityCards: Vector[Card] = threeCommunityCards :+ (7 of Diamonds)
  private val probabilitiesAfterTurnBeforeRiver = Probability.probabilities(playerHand, fourCommunityCards).right.get

  private val fiveCommunityCards = fourCommunityCards :+ (8 of Clubs)
  private val probabilitiesAfterTheRiver = Probability.probabilities(playerHand, fiveCommunityCards).right.get
}
