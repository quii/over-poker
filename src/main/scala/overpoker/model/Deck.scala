package overpoker.model

import scala.annotation.implicitNotFound

case class Deck(cards: Vector[Card]){
  lazy val size = cards.size
  lazy val sortedByRank: Vector[Card] = cards.sortBy(r=> -Rank.toInt(r.rank))
  def drawCardAt(index: Int) = (cards(index), Deck(removeAt(index)))
  private def removeAt(index: Int) = cards.slice(0, index) ++ cards.drop(index+1)
}

object Deck {
  val allSuits = Vector(Hearts, Clubs, Diamonds, Spades)

  val fullDeck = Deck(allSuits.flatMap(x => Suit(x).cards).toVector)

  @implicitNotFound("Bring a model.Randomiser in to scope or just import model.DefaultRandomiser")
  def draw(deck: Deck, amount: Int, addTo: Vector[Card] = Vector[Card]())(implicit randomiser:Randomiser): (Vector[Card], Deck) = amount match{
    case 0 => (addTo, deck)
    case drawMore => {
      val (drawn, newDeck) = deck.drawCardAt(randomiser.random(deck.size))
      draw(newDeck, drawMore-1, addTo :+ drawn)(randomiser)
    }
  }

  def flop(deck: Deck)(implicit r:Randomiser)= draw(deck, 3)(r)
  def turn(deck: Deck)(implicit r:Randomiser) = draw(deck, 1)(r)
  def river(deck: Deck)(implicit r:Randomiser) = draw(deck, 1)(r)

}
