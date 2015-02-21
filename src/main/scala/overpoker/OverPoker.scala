import overpoker.model.{HandValue, Hand, Deck}

object OverPoker extends App{
	val deck = Deck.fullDeck

	val (hand, deckAfterOneHand) = Hand.deal(deck)

	println("hand = " + hand)

	val (card1, card2, card3, deckPostFlop) = Deck.flop(deckAfterOneHand)

	// The flop
	println()

	val flop = List(card1, card2, card3)

	println("flop = " + flop)

	val afterFlop = HandValue.getValues(hand, flop)

	println("After flop you've got = " + afterFlop)

	// The turn
	println()

	val (theTurnCard, deckPostTheTurn) = deckPostFlop.draw

	val theTurn = flop :+ theTurnCard

	println("theTurn = " + theTurn)

	val afterTheTurn = HandValue.getValues(hand, theTurn)

	println("After the turn you've got = " + afterTheTurn)

	// The river
	println()

	val (theRiverCard, deckPostRiver) = deckPostTheTurn.draw

	val theRiver = theTurn :+ theRiverCard

	println("theRiver = " + theRiver)

	val result = HandValue.getValues(hand, theRiver)

	println("After the river youve got = " + result)
}
