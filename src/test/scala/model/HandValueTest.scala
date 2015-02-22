package model

import org.scalatest.FunSpec
import overpoker.model._
import org.scalatest.Matchers._
import Rank._

class HandValueTest extends FunSpec{

  import TestDeck._

  it("identifies a pair"){
    val pair = Hand(Queen of Clubs, Queen of Diamonds)
    HandValue.getValues(pair, Vector.empty) should contain(Pair(Queen))
  }

  it("identifies a pair from a hand and flop"){
    val hand = Hand(Queen of Clubs, 2 of Diamonds)
    val flop = Vector(Queen of Diamonds, 10 of Hearts, 8 of Clubs)
    HandValue.getValues(hand, flop) should contain(Pair(Queen))
  }

  it("identifies two pairs, ordered by rank"){
    val flopWithPair = Vector(Queen of Diamonds, Queen of Hearts, 8 of Clubs)
    HandValue.getValues(pairOf2, flopWithPair) should contain(TwoPair(Queen, 2))
  }

  it("identifies three of a kind"){
    val flopWithA2 = Vector(Queen of Diamonds, King of Hearts, 2 of Clubs)
    HandValue.getValues(pairOf2, flopWithA2) should contain(ThreeOfAKind(2))
  }

  it("identifies a fullhouse"){
    val flopWithAThreeOfAKind = Vector(Queen of Diamonds, Queen of Hearts, Queen of Clubs)
    HandValue.getValues(pairOf2, flopWithAThreeOfAKind) should contain(FullHouse(Queen, 2))
  }

  it("identifies four of a kind"){
    val flopWithTwoTwos = Vector(2 of Diamonds, 2 of Spades, Queen of Spades)
    HandValue.getValues(pairOf2, flopWithTwoTwos) should contain(FourOfAKind(2))
  }

  it("identifies a flush"){
    val flopWithHearts = Vector(4 of Hearts, Queen of Hearts, King of Hearts)
    val heartyHand = Hand(2 of Hearts, 5 of Hearts)
    HandValue.getValues(heartyHand, flopWithHearts) should contain(Flush(King))
  }

  it("identifies a straight"){
    val riverStraight = Vector(2 of Hearts, 7 of Hearts, 6 of Hearts, 8 of Spades, King of Clubs)
    val straightHand = Hand(5 of Hearts, 4 of Hearts)
    HandValue.getValues(straightHand, riverStraight) should contain(Straight(8))
  }

  it("identifies a straight starting with an ace"){
    val flopThatMakesAStraight = Vector(3 of Hearts, 4 of Clubs, 5 of Hearts)
    val straightHand = Hand(Ace of Clubs, 2 of Hearts)
    HandValue.getValues(straightHand, flopThatMakesAStraight) should contain(Straight(5))
  }

  it("identifies a straight flush"){
    val flopThatMakesAStraight = Vector(3 of Hearts, 4 of Hearts, 5 of Hearts)
    val straightHand = Hand(Ace of Hearts, 2 of Hearts)
    HandValue.getValues(straightHand, flopThatMakesAStraight) should contain(StraightFlush(5))
  }

  it("identifies a royal flush"){
    val flopThatMakesAStraight = Vector(Ace of Hearts, King of Hearts, Queen of Hearts)
    val straightHand = Hand(Jack of Hearts, 10 of Hearts)
    HandValue.getValues(straightHand, flopThatMakesAStraight) should contain(RoyalFlush)
  }

}
