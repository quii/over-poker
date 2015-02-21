package overpoker.model

case class Hand(card1: Card, card2: Card)

object Hand{
  def deal(deck: Deck):(Hand, Deck) = {
    val (cards, newDeck) = deck.draw(2)
    (Hand(cards(0), cards(1)), newDeck)
  }
}
