package model

import org.scalatest.FunSpec
import overpoker.model.Prediction._
import overpoker.model._
import overpoker.model.Rank._
import org.scalatest.Matchers._

class PredictionTest extends FunSpec{

  val uselessHand = Hand(2 of Clubs, 3 of Spades)

  it("figures out the odds of the river making a pair"){
    val turn = Vector(10 of Spades, King of Clubs, 5 of Diamonds, 6 of Clubs)
    oddsOfPair(uselessHand, turn) should (contain(Prediction(Pair(2), 15)) and contain(Prediction(Pair(3), 15)))
  }

}
