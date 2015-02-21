import overpoker.model.DefaultRandomiser._
import overpoker.model.{Deck, DefaultRandomiser, Hand, HandValue}

object OverPoker extends App{
	val deck = Deck.fullDeck

	val (hand, deckAfterOneHand) = Hand.deal(deck)

	println("hand = " + hand)

	val (flop, deckPostFlop) = Deck.flop(deckAfterOneHand)

	// The flop
	println()

	println("flop = " + flop)

	val afterFlop = HandValue.getValues(hand, flop)

	println("After flop you've got = " + afterFlop)

	// The turn
	println()

	val (theTurnCard, deckPostTheTurn) = Deck.draw(deckPostFlop, 1)

	val theTurn = flop :+ theTurnCard.head

	println("theTurn = " + theTurn)

	val afterTheTurn = HandValue.getValues(hand, theTurn)

	println("After the turn you've got = " + afterTheTurn)

	// The river
	println()

	val (theRiverCard, deckPostRiver) = Deck.draw(deckPostTheTurn, 1)

	val theRiver = theTurn :+ theRiverCard.head

	println("theRiver = " + theRiver)

	val result = HandValue.getValues(hand, theRiver)

	println("After the river youve got = " + result)
}
