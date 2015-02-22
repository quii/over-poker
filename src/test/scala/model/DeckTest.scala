package model

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.model._
import Rank._

class DeckTest extends FunSpec{

  import TestDeck._

  it("should create a deck with a full pack of cards"){
      Deck.fullDeck.size should be(52)
  }

  it("should remove cards from a deck"){
    val deck = Deck(Ace of Spades, King of Spades)
    val (drawn, newDeck) = deck.drawCardAt(0)

    drawn should be(Ace of Spades)
    newDeck should be(Deck(King of Spades))
  }

  it("should draw a card and return a new deck sans the picked card"){
    val (card, newDeck) = Deck.draw(testDeck,1)
    newDeck.size should be(testDeck.size -1)
    card.size should be(1)
    card.head should be(card1)
    newDeck.cards.contains(card) should be(false)
  }

  it("should be able to deal a flop"){
    val (cards, newDeck) = Deck.flop(testDeck)
    cards.toSet should be(Set(card1, card2, card3))
  }

  it("should sort cards by rank"){
    val deck = Deck(Ace of Spades, 2 of Clubs, 10 of Hearts)
    deck.sortedByRank should be(Vector(Ace of Spades,10 of Hearts, 2 of Clubs))
  }

}
