package model

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.model._

class DeckTest extends FunSpec{

  import TestDeck._

  it("should create a deck with a full pack of cards"){
      Deck.fullDeck.size should be(52)
  }

  it("should remove cards from a deck"){
    val deck = Deck(Vector(Card(Ace, Spades), Card(King, Spades)))
    val (drawn, newDeck) = deck.drawCardAt(0)

    drawn should be(Card(Ace, Spades))
    newDeck should be(Deck(Vector(Card(King, Spades))))
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
    val deck = Deck(Vector(Card(Ace, Spades), Card(2, Clubs), Card(10, Hearts)))
    deck.sortedByRank should be(Vector(Card(Ace, Spades),Card(10, Hearts), Card(2, Clubs)))
  }

}
