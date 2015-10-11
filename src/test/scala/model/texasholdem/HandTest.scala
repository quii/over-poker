package model.texasholdem

import model.texasholdem
import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.playingcards.Rank._
import overpoker.playingcards._
import overpoker.texasholdem._
import overpoker.texasholdem.hands._

class HandTest extends FunSpec{

  import model.TestDeck._

  it("finds a pair"){
    val cards = Vector(Queen of Clubs, Queen of Diamonds)
    Pair(cards) should be(Some(Pair(Queen)))
  }

  it("finds a pair from a hand and flop"){
    val hand = PlayerHand(Queen of Clubs, 2 of Diamonds)
    val flop = Flop(Queen of Diamonds, 10 of Hearts, 8 of Clubs)
    Hand.getValues(hand, flop) should contain(Pair(Queen))
  }

  it("should not find anything interesting"){
    val hand = PlayerHand(5 of Hearts, 2 of Spades)
    val flop = Flop(3 of Clubs, 8 of Diamonds, Jack of Clubs)
    Hand.getValues(hand, flop) should contain(HighCard(5, 2))
  }

  it("finds two pairs, ordered by rank"){
    val cards = Vector(Queen of Diamonds, Queen of Hearts, 8 of Clubs, 2 of Hearts, 2 of Diamonds)
    TwoPair(cards) should be(Some(TwoPair(Queen, 2)))
  }

  it("finds three of a kind"){
    val cards = Vector(Queen of Diamonds, King of Hearts, 2 of Clubs, 2 of Diamonds, 2 of Hearts)
    ThreeOfAKind(cards) should be(Some(ThreeOfAKind(2)))
  }

  it("finds a fullhouse"){
    val cards = Vector(Queen of Diamonds, Queen of Hearts, Queen of Clubs, 2 of Diamonds, 2 of Hearts)
    FullHouse(cards) should be(Some(FullHouse(Queen, 2)))
  }

  it("finds four of a kind"){
    val cards = Vector(2 of Diamonds, 2 of Spades, Queen of Spades, 2 of Diamonds, 2 of Hearts)
    FourOfAKind(cards) should be(Some(FourOfAKind(2)))
  }

  it("finds a flush"){
    val cards = Vector(4 of Hearts, Queen of Hearts, King of Hearts, 2 of Hearts, 5 of Hearts)
    Flush(cards) should be(Some(Flush(King)))
  }

  it("finds a straight"){
    val cards = Vector(2 of Hearts, 7 of Hearts, 6 of Hearts, 8 of Spades, King of Clubs, 5 of Hearts, 4 of Hearts)
    Straight(cards) should be(Some(Straight(8)))
  }

  it("finds a straight starting with an ace"){
    val cards = Vector(3 of Hearts, 4 of Clubs, 5 of Hearts, Ace of Clubs, 2 of Hearts)
    Straight(cards) should be(Some(Straight(5)))
  }

  it("finds a straight flush"){
    val flopThatMakesAStraight = Flop(3 of Hearts, 4 of Hearts, 5 of Hearts)
    val straightHand = PlayerHand(Ace of Hearts, 2 of Hearts)
    Hand.getValues(straightHand, flopThatMakesAStraight) should contain(StraightFlush(5))
  }

  it("finds a royal flush"){
    val flopThatMakesAStraight = Flop(Ace of Hearts, King of Hearts, Queen of Hearts)
    val straightHand = PlayerHand(Jack of Hearts, 10 of Hearts)
    Hand.getValues(straightHand, flopThatMakesAStraight) should contain(RoyalFlush)
  }

  it("returns the hands in descending order (head is best, last is worst)")(pending)

}
