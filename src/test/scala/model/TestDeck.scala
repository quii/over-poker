package model

import overpoker.model._
import Rank._

object TestDeck {

  implicit val drawFromTheEnd = new Randomiser {
    def random(size: Int) = 0
  }

  val worstHand = Hand(2 of Clubs, 3 of Hearts)
  val pairOf2 = Hand(2 of Clubs, 2 of Hearts)
}
