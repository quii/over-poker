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
      val turn = Vector(10 of Spades, King of Clubs, 5 of Diamonds, 6 of Clubs)
      oddsOfPair(uselessHand, turn) should (contain(Prediction(Pair(2), 6.5)) and contain(Prediction(Pair(3), 6.5)))
    }

    it("knows you cant make a flush"){
      val turn = Vector(2 of Hearts, 10 of Spades, 6 of Diamonds, King of Clubs)
      oddsOfFlush(uselessHand, turn) should be('empty)
    }

    it("figures out the odds of you making a flush"){
      val turn = Vector(10 of Clubs, 9 of Clubs, Ace of Hearts, Queen of Diamonds)
      oddsOfFlush(flushingHand, turn) should contain(Prediction(Flush(10), 19.6))
    }

  }
}
