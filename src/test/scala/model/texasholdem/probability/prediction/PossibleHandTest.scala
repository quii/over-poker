package model.texasholdem.probability.prediction

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.texasholdem.prediction.PossibleHands

class PossibleHandTest extends FunSpec {

  describe("All possible hands") {
    it("contains 52 choose 5 hands") {
      val fiftyTwoChooseFive = 2598960
      PossibleHands.allHands.size should be(fiftyTwoChooseFive)
    }
  }
  
  describe("Possible hands before the flop") {
    it("contains all hands that contain player card 1 or player card 2 (including both card 1 and 2)") (pending)
  }
  
  describe("Possible hands after the flop and before the turn") {
    it("contains all hands that contain player card 1 AND 2 and AT LEAST 1 community card") (pending)
    it("contains all hands that contain player card 1 OR 2 and AT LEAST 2 community cards") (pending)
  }
  
  describe("Possible hands after the turn and before the river"){
    it("contains all hands that contain player card 1 AND 2 and AT LEAST 2 community cards")(pending)
    it("contains all hands that contain player card 1 OR 2 and AT LEAST 3 community cards")(pending)
  }
  
}
