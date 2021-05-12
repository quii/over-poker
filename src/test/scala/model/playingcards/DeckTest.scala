package model.playingcards

import org.scalatest.matchers.must.Matchers._
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.funspec.AnyFunSpec
import overpoker.playingcards.Rank._
import overpoker.playingcards._

class DeckTest extends AnyFunSpec{

  import model.TestDeck._

  val deck = Deck(Ace of Spades, King of Spades, 5 of Hearts, 2 of Spades, Jack of Clubs)

  it("should create a deck with a full pack of cards"){
      Deck.fullDeck.size should be(52)
  }

  it("should draw cards and return a new deck sans the picked cards"){
    val twoCards = 2
    val (drawn, newDeck) = deck draw twoCards

    newDeck.size should be(deck.size -twoCards)
    newDeck.cards.head should be(5 of Hearts)

    drawn should (contain(Ace of Spades) and contain(King of Spades))
  }

  it("should be able to deal a flop"){
    val (cards, newDeck) = deck.flop
    cards should (contain(Ace of Spades) and contain(King of Spades) and contain(5 of Hearts))
  }

  it("should sort cards by rank"){
    val deck = Deck(Ace of Spades, 2 of Clubs, 10 of Hearts)
    deck.sortedByRank should be(Vector(Ace of Spades,10 of Hearts, 2 of Clubs))
  }

}
