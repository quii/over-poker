package model

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.model._
import Rank._

class DeckTest extends FunSpec{

  import TestDeck._

  val deck = Deck(Ace of Spades, King of Spades, 5 of Hearts, 2 of Spades, Jack of Clubs)

  it("should create a deck with a full pack of cards"){
      Deck.fullDeck.size should be(52)
  }

  it("should draw cards and return a new deck sans the picked cards"){
    val cardsToTake = 2
    val (drawn, newDeck) = Deck.draw(deck,cardsToTake)

    newDeck.size should be(deck.size -cardsToTake)
    newDeck.cards(0) should be(5 of Hearts)

    drawn.size should be(cardsToTake)
    drawn(0) should be(Ace of Spades)
    drawn(1) should be(King of Spades)
  }

  it("should be able to deal a flop"){
    val (cards, newDeck) = Deck.flop(deck)
    cards.toSet should be(Set(Ace of Spades, King of Spades, 5 of Hearts))
  }

  it("should sort cards by rank"){
    val deck = Deck(Ace of Spades, 2 of Clubs, 10 of Hearts)
    deck.sortedByRank should be(Vector(Ace of Spades,10 of Hearts, 2 of Clubs))
  }

}
