package overpoker.texasholdem.prediction

import overpoker.playingcards.{Card, PlayerHand}


case class TexasHoldemProbabilities(flop: Double, turn: Double, river: Double)

object Probability {

  def ofPair(playerHand: PlayerHand, communityCards: Vector[Card]): TexasHoldemProbabilities = {
    TexasHoldemProbabilities(flop = 1.0, turn = 1.0, river = 1.0)
  }

}
