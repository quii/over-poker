package model

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.model._

class DeckTest extends FunSpec{

  import TestDeck._

  it("should create a deck with a full pack of cards"){
      Deck.fullDeck.size should be(52)
  }

  it("should draw a card and return a new deck sans the picked card"){
    val (card, newDeck) = testDeck.draw
    newDeck.size should be(testDeck.size -1)
    card should be(card1)
    newDeck.cards.contains(card) should be(false)
  }

  it("should be able to deal a flop"){
    val (drawnCard1, drawnCard2, drawnCard3, newDeck) = Deck.flop(testDeck)

    drawnCard1 should be(card1)
    drawnCard2 should be(card2)
    drawnCard3 should be(card3)

    newDeck.cards.contains(drawnCard1) should be(false)
    newDeck.cards.contains(drawnCard2) should be(false)
    newDeck.cards.contains(drawnCard3) should be(false)
  }

}
