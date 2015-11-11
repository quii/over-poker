package prediction.unit

import org.scalatest.FunSpec
import org.scalatest.Matchers._

import overpoker.playingcards._
import overpoker.playingcards.Rank._
import prediction.{AnyRoyalFlush, Probability}

class ProbabilityTest extends FunSpec {

  describe("The probability calculator") {

    it("works out the correct probability for a Royal Flush") {
      val playerCards = Vector(Ace of Spades, King of Spades)
      val deck = Vector(Queen of Spades, Jack of Spades, 10 of Spades, 9 of Spades)
      Probability.probability(playerCards, deck, 3)(AnyRoyalFlush) should be(0.25)
    }

    it("doesn't take player cards into account when working out the probabilities") {
      val playerCards = Vector(Ace of Spades, King of Spades)
      val deck = Vector(Ace of Spades, King of Spades, Queen of Spades, Jack of Spades, 10 of Spades, 9 of Spades)
      Probability.probability(playerCards, deck, 3)(AnyRoyalFlush) should be(0.25)
    }

  }

}
