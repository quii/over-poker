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

  it("figures out the odds of the river making a pair"){
    val turn = Vector(10 of Spades, King of Clubs, 5 of Diamonds, 6 of Clubs)
    oddsOfPair(uselessHand, turn) should (contain(Prediction(Pair(2), 15)) and contain(Prediction(Pair(3), 15)))
  }

}
