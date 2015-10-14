package model.texasholdem.probability.checkers

import overpoker.playingcards.{Card, Deck, PlayerHand}
import overpoker.texasholdem.hands.{Hand, Pair}

object BruteForcePairBeforeFlop {
	def main(args: Array[String]) {
		// Start with a full deck
    val deck = Deck.fullDeck

    // Make sure you deal out a hand that isn't a pair
    val (cardOne, deckAfterDraw1) = deck.drawCardAt(0)
    val (cardTwo, deckAfterDraw2) = deckAfterDraw1.drawCardAt(0)
    val (hand, deckAfterOneHand) = (PlayerHand(cardOne, cardTwo), deckAfterDraw2)
    if (hand.card1.rank == hand.card2.rank) throw new Error

		val cards: Vector[Card] = deckAfterOneHand.cards

		println("Number of all possible flops: " + allPossibleFlops(cards).length)

		var numPairs: Int = 0
		val flops: Vector[Set[Card]] = allPossibleFlops(cards)
		flops.foreach{flop =>
			if(Hand.getValues(hand, flop.toVector).head.isInstanceOf[Pair]) numPairs = numPairs + 1
		}

		println("Number of pairs: " + numPairs)
	}

  def allPossibleFlops(cards: Vector[Card]): Vector[Set[Card]] = {
    var possibleFlops: Vector[Set[Card]] = Vector()

    cards.foreach{ cardOne =>
      cards.foreach { cardTwo =>
        cards.foreach { cardThree =>
          if (cardOne != cardTwo && cardTwo != cardThree && cardOne != cardThree) {
            possibleFlops = possibleFlops :+ Set(cardOne, cardTwo, cardThree)
          }
        }
      }
    }
    possibleFlops.distinct
  }

}
