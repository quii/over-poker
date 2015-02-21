package model

import org.scalatest.FunSpec
import overpoker.model._
import org.scalatest.Matchers._

class HandValueTest extends FunSpec{

  import TestDeck._

  it("identifies a pair"){
    val pair = Hand(Card(Queen,Clubs), Card(Queen,Diamonds))
    HandValue.getValues(pair, Vector.empty) should contain(Pair(Queen))
  }

  it("identifies a pair from a hand and flop"){
    val hand = Hand(Card(Queen,Clubs), Card(2,Diamonds))
    val flop = Vector(Card(Queen,Diamonds), Card(10,Hearts), Card(8,Clubs))
    HandValue.getValues(hand, flop) should contain(Pair(Queen))
  }

  it("identifies two pairs, ordered by rank"){
    val flopWithPair = Vector(Card(Queen,Diamonds), Card(Queen,Hearts), Card(8,Clubs))
    HandValue.getValues(pairOf2, flopWithPair) should contain(TwoPair(Queen, 2))
  }

  it("identifies three of a kind"){
    val flopWithA2 = Vector(Card(Queen,Diamonds), Card(King,Hearts), Card(2,Clubs))
    HandValue.getValues(pairOf2, flopWithA2) should contain(ThreeOfAKind(2))
  }

  it("identifies a fullhouse"){
    val flopWithAThreeOfAKind = Vector(Card(Queen,Diamonds), Card(Queen,Hearts), Card(Queen,Clubs))
    HandValue.getValues(pairOf2, flopWithAThreeOfAKind) should contain(FullHouse(Queen, 2))
  }

  it("identifies four of a kind"){
    val flopWithTwoTwos = Vector(Card(2, Diamonds), Card(2, Spades), Card(Queen, Spades))
    HandValue.getValues(pairOf2, flopWithTwoTwos) should contain(FourOfAKind(2))
  }

  it("identifies a flush"){
    val flopWithHearts = Vector(Card(4,Hearts), Card(Queen,Hearts), Card(King,Hearts))
    val heartyHand = Hand(Card(2,Hearts), Card(5,Hearts))
    HandValue.getValues(heartyHand, flopWithHearts) should contain(Flush(King))
  }

  it("identifies a straight"){
    val riverStraight = Vector(Card(2,Hearts), Card(7,Hearts), Card(6,Hearts), Card(8, Spades), Card(King, Clubs))
    val straightHand = Hand(Card(5,Hearts), Card(4,Hearts))
    HandValue.getValues(straightHand, riverStraight) should contain(Straight(8))
  }

  it("identifies a straight starting with an ace"){
    val flopThatMakesAStraight = Vector(Card(3,Hearts), Card(4,Clubs), Card(5,Hearts))
    val straightHand = Hand(Card(Ace,Clubs), Card(2,Hearts))
    HandValue.getValues(straightHand, flopThatMakesAStraight) should contain(Straight(5))
  }

  it("identifies a straight flush"){
    val flopThatMakesAStraight = Vector(Card(3,Hearts), Card(4,Hearts), Card(5,Hearts))
    val straightHand = Hand(Card(Ace,Hearts), Card(2,Hearts))
    HandValue.getValues(straightHand, flopThatMakesAStraight) should contain(StraightFlush(5))
  }

  it("identifies a royal flush"){
    val flopThatMakesAStraight = Vector(Card(Ace,Hearts), Card(King,Hearts), Card(Queen,Hearts))
    val straightHand = Hand(Card(Jack,Hearts), Card(10,Hearts))
    HandValue.getValues(straightHand, flopThatMakesAStraight) should contain(RoyalFlush)
  }

}
