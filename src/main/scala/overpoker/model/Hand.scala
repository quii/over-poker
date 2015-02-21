package overpoker.model

case class Hand(card1: Card, card2: Card)

object Hand{
  def deal(deck: Deck):(Hand, Deck) = {
    val (card1, deckMinusOne) = deck.draw
    val (card2, deckMinusTwo) = deckMinusOne.draw
    (Hand(card1, card2), deckMinusTwo)
  }
}
