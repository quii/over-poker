package overpoker.model

import scala.util.Random

case class Deck(cards: Vector[Card])(implicit randomiser: Randomiser) {

  def draw(amount: Int = 1): (List[Card], Deck) = {
    val drawn = for(i <- 1 to amount) yield cards(randomiser.random(cards.size))
    val newDeck = cards.toSet.diff(drawn.toSet)
    (drawn.toList, Deck(newDeck.toVector))
  }

  lazy val size = cards.size
}

object Deck {
  val allSuits = Vector(Hearts, Clubs, Diamonds, Spades)

  implicit def randomiser = new Randomiser {
    override def random(size: Int): Int = {
      val rnd = new Random
      rnd.nextInt(size)
    }
  }

  val fullDeck = Deck(allSuits.flatMap(x => Suit(x).cards).toVector)

  def flop(deck: Deck): (Card, Card, Card, Deck) = {
    val (cards, newDeck) = deck.draw(3)
    (cards(0),cards(1), cards(2), newDeck)
  }
}

trait Randomiser{
  def random(size: Int): Int
}