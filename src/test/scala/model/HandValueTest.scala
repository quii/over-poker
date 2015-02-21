package model

import org.scalatest.FunSpec
import overpoker.model._
import org.scalatest.Matchers._

class HandValueTest extends FunSpec{

  import TestDeck._

  it("identifies a pair"){
    val pair = Hand(Card(Queen,Clubs), Card(Queen,Diamonds))
    HandValue.getValue(pair, List.empty) should be(Pair(Queen))
  }

  it("identifies a pair from a hand and flop"){
    val hand = Hand(Card(Queen,Clubs), Card(2,Diamonds))
    val flop = List(Card(Queen,Diamonds), Card(10,Hearts), Card(8,Clubs))
    HandValue.getValue(hand, flop) should be(Pair(Queen))
  }

  it("identifies two pairs, ordered by rank"){
    val flopWithPair = List(Card(Queen,Diamonds), Card(Queen,Hearts), Card(8,Clubs))
    HandValue.getValue(pairOf2, flopWithPair) should be(TwoPair(Queen, 2))
  }

  it("identifies three of a kind"){
    val flopWithA2 = List(Card(Queen,Diamonds), Card(King,Hearts), Card(2,Clubs))
    HandValue.getValue(pairOf2, flopWithA2) should be(ThreeOfAKind(2))
  }

  it("identifies a fullhouse"){
    val flopWithAThreeOfAKind =  List(Card(Queen,Diamonds), Card(Queen,Hearts), Card(Queen,Clubs))
    HandValue.getValue(pairOf2, flopWithAThreeOfAKind) should be(FullHouse(Queen, 2))
  }

  it("identifies a flush"){
    val flopWithHearts = List(Card(4,Hearts), Card(Queen,Hearts), Card(King,Hearts))
    val heartyHand = Hand(Card(2,Hearts), Card(5,Hearts))
    HandValue.getValue(heartyHand, flopWithHearts) should be(Flush(King))
  }
}
