package model

import org.scalatest.FunSpec
import overpoker.model._
import org.scalatest.Matchers._

class HandValueTest extends FunSpec{

  import TestDeck._

  it("identifies a pair"){
    val pair = Hand(Card(Queen)(Clubs), Card(Queen)(Diamonds))
    HandValue.getValue(pair, List.empty) should be(Pair(Queen))
  }

  it("identifies a pair from a hand and flop"){
    val hand = Hand(Card(Queen)(Clubs), Card(2)(Diamonds))
    val flop = List(Card(Queen)(Diamonds), Card(10)(Hearts), Card(8)(Clubs))

    HandValue.getValue(hand, flop) should be(Pair(Queen))
  }

  it("identifies the highest pair"){
    val flopWithPair = List(Card(Queen)(Diamonds), Card(Queen)(Hearts), Card(8)(Clubs))
    HandValue.getValue(worstPair, flopWithPair) should be(Pair(Queen))
  }
}
