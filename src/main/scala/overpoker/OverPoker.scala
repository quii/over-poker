import overpoker.playingcards.DefaultRandomiser._
import overpoker.playingcards.{Deck, DefaultRandomiser, Hand}
import overpoker.texasholdem.HandValue

object OverPoker extends App{
	val deck = Deck.fullDeck

	val (hand, deckAfterOneHand) = Hand.deal(deck)

	println("hand = " + hand)

	val (flop, deckPostFlop) = deckAfterOneHand.flop

	// The flop
	println()

	println("flop = " + flop)

	val afterFlop = HandValue.getValues(hand, flop)

	println("After flop you've got = " + afterFlop)

	// The turn
	println()

	val (theTurnCard, deckPostTheTurn) = deckPostFlop.turn

	val theTurn = flop :+ theTurnCard.head

	println("theTurn = " + theTurn)

	val afterTheTurn = HandValue.getValues(hand, theTurn)

	println("After the turn you've got = " + afterTheTurn)

	// The river
	println()

	val (theRiverCard, deckPostRiver) = deckPostTheTurn.river

	val theRiver = theTurn :+ theRiverCard.head

	println("theRiver = " + theRiver)

	val result = HandValue.getValues(hand, theRiver)

	println("After the river youve got = " + result)
}
