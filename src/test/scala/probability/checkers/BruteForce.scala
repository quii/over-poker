package probability.checkers

import overpoker.playingcards.{Card, Deck, PlayerHand}
import overpoker.texasholdem.hands.{Hand, Pair}

object BruteForce {
	def main(args: Array[String]) {
		val deck = Deck.fullDeck
		val (hand, deckAfterOneHand) = PlayerHand.deal(deck)

		val cards: Vector[Card] = deckAfterOneHand.cards
		println(cards.length)
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

		println(allPossibleFlops(cards).length)

		var numPairs: Int = 0
		val flops: Vector[Set[Card]] = allPossibleFlops(cards)
		flops.foreach{flop =>
			if(Hand.getValues(hand, flop.toVector).head.isInstanceOf[Pair]) numPairs = numPairs + 1
		}

		println("Number of pairs: " + numPairs)

	}
}
