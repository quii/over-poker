package offline

import overpoker.playingcards.{Deck, PlayerHand}
import overpoker.texasholdem.hands.Hand

case class Probabilities(playerHand: PlayerHand, flop: HandProbabilities)

object Probabilities {

  def preFlop: Vector[Probabilities] = {
    val playerHands = Deck.fullDeck.cards.combinations(2).map(cards => PlayerHand(cards.head, cards(1))).toVector
    playerHands.map(probabilities)
  }

  def probabilities(playerHand: PlayerHand): Probabilities = {
    Probabilities(playerHand, flop = flopProbabilities(playerHand))
  }

  def flopProbabilities(playerHand: PlayerHand): HandProbabilities = {
    val allFlops = Deck.fullDeck.cards.filter(card => card != playerHand.card1 && card != playerHand.card2).combinations(3).toVector
    val allHands: Vector[Hand] = allFlops.map(flop => Hand.playersBestHand(playerHand, flop))
//    allHands.groupBy()
    HandProbabilities()
  }
}


case class HandProbabilities(
                              royalFlush: Double = -100.0,
                              straightFlush: Double = -100.0,
                              fourOfAKind: Double = -100.0,
                              fullHouse: Double = -100.0,
                              flush: Double = -100.0,
                              straight: Double = -100.0,
                              threeOfAKind: Double = -100.0,
                              twoPair: Double = -100.0,
                              pair: Double = -100.0,
                              highCard: Double = -100.0,
                              nothing: Double = -100.0)
