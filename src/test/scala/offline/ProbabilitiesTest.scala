package offline

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import org.scalamock.scalatest.MockFactory
import overpoker.playingcards.{Clubs, Spades, PlayerHand, Deck}
import overpoker.playingcards.Rank._

class ProbabilitiesTest extends FunSpec with ProbabilityMatchers with MockFactory {

  describe("Pre-flop Probability Generation") {
    it("contains an entry for every possible player hand") {
      val deck = Deck.fullDeck.cards
      val playerHands = deck.combinations(2).toVector.map(cards => PlayerHand(cards.head, cards(1)))
      Probabilities.preFlop should containAll(playerHands)
    }
  }

  describe("Per hand probability") {
    it("contains the player hand") {
      val playerHand = PlayerHand(5 of Spades, 10 of Clubs)
      Probabilities.probabilities(playerHand).playerHand should be(playerHand)
    }
  }
}
