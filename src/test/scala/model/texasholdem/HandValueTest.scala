package model.texasholdem

import model.texasholdem
import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.playingcards.Rank._
import overpoker.playingcards._
import overpoker.texasholdem._

class HandValueTest extends FunSpec{

  import model.TestDeck._

  it("finds a pair"){
    val pair = Hand(Queen of Clubs, Queen of Diamonds)
    HandValue.getValues(pair, Vector.empty) should contain(Pair(Queen))
  }

  it("finds a pair from a hand and flop"){
    val hand = Hand(Queen of Clubs, 2 of Diamonds)
    val flop = Flop(Queen of Diamonds, 10 of Hearts, 8 of Clubs)
    HandValue.getValues(hand, flop) should contain(Pair(Queen))
  }

  it("should not find anything interesting"){
    val hand = Hand(5 of Hearts, 2 of Spades)
    val flop = Flop(3 of Clubs, 8 of Diamonds, Jack of Clubs)
    HandValue.getValues(hand, flop) should contain(HighCard(5, 2))
  }

  it("finds two pairs, ordered by rank"){
    val flopWithPair = Flop(Queen of Diamonds, Queen of Hearts, 8 of Clubs)
    HandValue.getValues(pairOf2, flopWithPair) should contain(TwoPair(Queen, 2))
  }

  it("finds three of a kind"){
    val flopWithA2 = Flop(Queen of Diamonds, King of Hearts, 2 of Clubs)
    HandValue.getValues(pairOf2, flopWithA2) should contain(ThreeOfAKind(2))
  }

  it("finds a fullhouse"){
    val flopWithAThreeOfAKind = Flop(Queen of Diamonds, Queen of Hearts, Queen of Clubs)
    HandValue.getValues(pairOf2, flopWithAThreeOfAKind) should contain(FullHouse(Queen, 2))
  }

  it("finds four of a kind"){
    val flopWithTwoTwos = Flop(2 of Diamonds, 2 of Spades, Queen of Spades)
    HandValue.getValues(pairOf2, flopWithTwoTwos) should contain(FourOfAKind(2))
  }

  it("finds a flush"){
    val flopWithHearts = Flop(4 of Hearts, Queen of Hearts, King of Hearts)
    val heartyHand = Hand(2 of Hearts, 5 of Hearts)
    HandValue.getValues(heartyHand, flopWithHearts) should contain(Flush(King))
  }

  it("finds a straight"){
    val riverStraight = River(2 of Hearts, 7 of Hearts, 6 of Hearts, 8 of Spades, King of Clubs)
    val straightHand = Hand(5 of Hearts, 4 of Hearts)
    HandValue.getValues(straightHand, riverStraight) should contain(Straight(8))
  }

  it("finds a straight starting with an ace"){
    val flopThatMakesAStraight = Flop(3 of Hearts, 4 of Clubs, 5 of Hearts)
    val straightHand = Hand(Ace of Clubs, 2 of Hearts)
    HandValue.getValues(straightHand, flopThatMakesAStraight) should contain(Straight(5))
  }

  it("finds a straight flush"){
    val flopThatMakesAStraight = Flop(3 of Hearts, 4 of Hearts, 5 of Hearts)
    val straightHand = Hand(Ace of Hearts, 2 of Hearts)
    HandValue.getValues(straightHand, flopThatMakesAStraight) should contain(StraightFlush(5))
  }

  it("finds a royal flush"){
    val flopThatMakesAStraight = Flop(Ace of Hearts, King of Hearts, Queen of Hearts)
    val straightHand = Hand(Jack of Hearts, 10 of Hearts)
    HandValue.getValues(straightHand, flopThatMakesAStraight) should contain(RoyalFlush)
  }

}
