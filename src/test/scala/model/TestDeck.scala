package model

import overpoker.model._

object TestDeck {
  val card1 = Card(Ace)(Spades)
  val card2 = Card(2)(Clubs)
  val card3 = Card(Jack)(Diamonds)
  val card4 = Card(8)(Spades)
  val card5 = Card(5)(Hearts)

  implicit val drawFromTheTop = new Randomiser {
    def random(size: Int) = 0
  }

  val testDeck = new Deck(Vector(card1, card2, card3, card4, card5))(drawFromTheTop)
}
