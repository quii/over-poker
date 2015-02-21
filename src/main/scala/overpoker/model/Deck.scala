package overpoker.model

import scala.util.Random

case class Deck(cards: Vector[Card])(implicit randomiser: Randomiser) {
  def draw: (Card, Deck) = {
    val randomCard: Card = cards.toVector(randomiser.random(cards.size))
    (randomCard, Deck(cards filterNot(_==randomCard)))
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
    val (card1, deck1) = deck.draw
    val (card2, deck2) = deck1.draw
    val (card3, deck3) = deck2.draw
    (card1, card2, card3, deck3)
  }
}

trait Randomiser{
  def random(size: Int): Int
}