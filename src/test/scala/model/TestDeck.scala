package model

import overpoker.playingcards._
import Rank._

object TestDeck {

  implicit val drawFromTheEnd = new Randomiser {
    def random(size: Int) = 0
  }

  val worstHand = PlayerHand(2 of Clubs, 3 of Hearts)
  val pairOf2 = PlayerHand(2 of Clubs, 2 of Hearts)
}
