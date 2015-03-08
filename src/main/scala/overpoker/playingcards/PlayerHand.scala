package overpoker.playingcards

case class PlayerHand(card1: Card, card2: Card)
import overpoker.playingcards.DefaultRandomiser._

object PlayerHand{
  def deal(deck: Deck):(PlayerHand, Deck) = {
    val (cards, newDeck) = deck.draw(2)
    (PlayerHand(cards(0), cards(1)), newDeck)
  }
}
