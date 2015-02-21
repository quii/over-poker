package overpoker.model

case class Hand(card1: Card, card2: Card)
import overpoker.model.DefaultRandomiser._

object Hand{
  def deal(deck: Deck):(Hand, Deck) = {
    val (cards, newDeck) = Deck.draw(deck, 2)
    (Hand(cards(0), cards(1)), newDeck)
  }
}
