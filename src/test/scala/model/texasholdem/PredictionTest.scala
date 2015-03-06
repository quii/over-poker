package model.texasholdem

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.playingcards.Rank._
import overpoker.playingcards._
import overpoker.texasholdem._
import overpoker.texasholdem.Prediction
import overpoker.texasholdem.Prediction._

class PredictionTest extends FunSpec{

  val uselessHand = Hand(2 of Clubs, 3 of Spades)
  val flushingHand = Hand(2 of Clubs, 3 of Clubs)

  describe("the river"){

    it("figures out the odds of making a pair"){
      val turn = Turn(10 of Spades, King of Clubs, 5 of Diamonds, 6 of Clubs)
      val chanceOfPair = 6.5
      oddsOfPair(uselessHand, turn) should (contain(Prediction(Pair(2), chanceOfPair)) and contain(Prediction(Pair(3), chanceOfPair)))
    }

    it("knows you cant make a flush"){
      val turn = Turn(2 of Hearts, 10 of Spades, 6 of Diamonds, King of Clubs)
      oddsOfFlush(uselessHand, turn) should be('empty)
    }

    it("knows you cant make three of a kind")(pending)

    it("figures out you odds of three of a kind")(pending)

    it("figures out the odds of you making a flush"){
      val turn = Turn(10 of Clubs, 9 of Clubs, Ace of Hearts, Queen of Diamonds)
      val chanceOfFlush = 19.6
      oddsOfFlush(flushingHand, turn) should contain(Prediction(Flush(10), chanceOfFlush))
    }

    it("knows you cant make a full house"){
      val turn = Turn(10 of Clubs, 9 of Spades, Ace of Spades, Queen of Hearts)
      oddsOfFullHouse(uselessHand, turn) should be('empty)
    }

    it("figures out the odds of a full house"){
      val pocketPair = Hand(2 of Clubs, 2 of Spades)
      val turnWithAPair = Turn(10 of Clubs, 10 of Spades, Ace of Spades, Queen of Hearts)
      oddsOfFullHouse(pocketPair, turnWithAPair) should (contain(Prediction(FullHouse(10, 2), 4.3)) and contain(Prediction(FullHouse(2, 10), 4.3)))
    }

    it("knows you cant make a straight")(pending)

    it("figures out the odds of a straight")(pending)

    it("figures out the odds of an inside straight")(pending)

  }
}
