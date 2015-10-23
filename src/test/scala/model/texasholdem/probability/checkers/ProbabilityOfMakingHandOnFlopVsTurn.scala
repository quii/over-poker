package model.texasholdem.probability.checkers

import overpoker.playingcards.{Deck, PlayerHand}
import overpoker.texasholdem.hands.{Hand, Pair}

object ProbabilityOfMakingHandOnFlopVsTurn {

  def main(args: Array[String]) {

    val (cardOne, deckAfterOne) = Deck.fullDeck.drawCardAt(0)
    val (cardTwo, deck) = deckAfterOne.drawCardAt(0)

    val playerHand = PlayerHand(cardOne, cardTwo)

    // P(Pair on flop)
    val allPossibleFlopVariations = deck.cards.combinations(3).toList
    val allFlopHands = for {
      possibleFlopVariation <- allPossibleFlopVariations
    } yield Hand.getValues(playerHand, possibleFlopVariation)

    val numPairHandsFlop: Int = allFlopHands.count(_.head.isInstanceOf[Pair])
    println("On the flop:")
    println("Num Pair Hands: " + numPairHandsFlop)
    println("Num Possible flop hands: " + allPossibleFlopVariations.length)
    println("P(Pair on flop) = " + (numPairHandsFlop.toDouble / allPossibleFlopVariations.length.toDouble))


    // P(Pair on turn)
    val allPossibleTurnVariations = deck.cards.combinations(4).toList
    val allTurnHands = for {
      possibleTurnVariation <- allPossibleTurnVariations
    } yield Hand.getValues(playerHand, possibleTurnVariation)

    val numPairHandsTurn = allTurnHands.count(_.head.isInstanceOf[Pair])
    println()
    println("On the Turn:")
    println("Num Pair Hands: " + numPairHandsTurn)
    println("Num Possible turn hands: " + allPossibleTurnVariations.length)
    println("P(Pair on flop) = " + (numPairHandsTurn.toDouble / allPossibleTurnVariations.length.toDouble))
  }
  
}
