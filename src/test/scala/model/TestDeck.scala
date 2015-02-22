package model

import overpoker.model._
import Rank._

object TestDeck {
  val card1 = Ace of Spades
  val card2 = 2 of Clubs
  val card3 = Jack of Diamonds
  val card4 = 8 of Spades
  val card5 = 5 of Hearts

  implicit val drawFromTheEnd = new Randomiser {
    def random(size: Int) = 0
  }

  val testDeck = Deck(card1, card2, card3, card4, card5)

  val worstHand = Hand(2 of Clubs, 3 of Hearts)
  val pairOf2 = Hand(2 of Clubs, 2 of Hearts)
}
