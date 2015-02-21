package model

import org.scalatest.FunSpec
import overpoker.model.Hand
import org.scalatest.Matchers._

class HandTest extends FunSpec {

  import TestDeck._

  it("deals a hand"){
    val (hand, remainingCards) = Hand.deal(testDeck)

    hand.card1 should be(card1)
    hand.card2 should be(card2)
    remainingCards.cards should not contain(card1, card2)
  }

}
