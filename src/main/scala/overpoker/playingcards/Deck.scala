package overpoker.playingcards

import overpoker.playingcards.Deck.DrawnCardsAndDeck

case class Deck(cards: Vector[Card]){

  lazy val size = cards.size
  lazy val sortedByRank: Vector[Card] = cards.sortBy(r=> -Rank.toInt(r.rank))

  def flop(implicit randomiser:Randomiser) = draw(3)
  def turn(implicit randomiser:Randomiser) = draw(1)
  def river(implicit randomiser:Randomiser) = draw(1)

  def draw(amount: Int)(implicit randomiser:Randomiser):DrawnCardsAndDeck = {

    def drawCards(deck: Deck, amount: Int, addTo: Vector[Card]):DrawnCardsAndDeck = amount match{
      case 0 => (addTo, deck)
      case drawMore => {
        val (drawn, newDeck) = deck.drawCardAt(randomiser.random(deck.size))
        drawCards(newDeck, drawMore-1, addTo :+ drawn)
      }
    }
    drawCards(this, amount, Vector[Card]())
  }

  def drawCardAt(index: Int) = (cards(index), Deck(removeAt(index)))
  private def removeAt(index: Int) = cards.slice(0, index) ++ cards.drop(index+1)
}

object Deck {

  type DrawnCardsAndDeck = (Vector[Card], Deck)

  def apply(cards: Card*):Deck = Deck(cards.toVector)

  val fullDeck = Deck(Vector(Hearts, Clubs, Diamonds, Spades).flatMap(_.cards))

}
