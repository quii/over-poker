package overpoker.texasholdem.prediction

import overpoker.playingcards.{Card, PlayerHand}


case class TexasHoldemProbabilities(flop: Double, turn: Double, river: Double)

object Probability {

  def ofPair(playerHand: PlayerHand, communityCards: Vector[Card]): TexasHoldemProbabilities = {
    def isPair(playerHand: PlayerHand) = playerHand.card1.rank == playerHand.card2.rank

    if (isPair(playerHand)) TexasHoldemProbabilities(flop = 1.0, turn = 1.0, river = 1.0)
    else TexasHoldemProbabilities(flop = 0.40408163265, turn = 0, river = 0)
  }

}
