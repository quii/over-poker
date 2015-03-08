import overpoker.playingcards.DefaultRandomiser._
import overpoker.playingcards.{Deck, PlayerHand}
import overpoker.texasholdem.hands.Hand

object OverPoker extends App{
	val deck = Deck.fullDeck

	val (hand, deckAfterOneHand) = PlayerHand.deal(deck)

	println("hand = " + hand)

	val (flop, deckPostFlop) = deckAfterOneHand.flop

	// The flop
	println()

	println("flop = " + flop)

	val afterFlop = Hand.getValues(hand, flop)

	println("After flop you've got = " + afterFlop)

	// The turn
	println()

	val (theTurnCard, deckPostTheTurn) = deckPostFlop.turn

	val theTurn = flop :+ theTurnCard.head

	println("theTurn = " + theTurn)

	val afterTheTurn = Hand.getValues(hand, theTurn)

	println("After the turn you've got = " + afterTheTurn)

	// The river
	println()

	val (theRiverCard, deckPostRiver) = deckPostTheTurn.river

	val theRiver = theTurn :+ theRiverCard.head

	println("theRiver = " + theRiver)

	val result = Hand.getValues(hand, theRiver)

	println("After the river youve got = " + result)
}
