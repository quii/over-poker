import overpoker.model.{Hand, Deck}

object OverPoker extends App{
	val deck = Deck.fullDeck

	val (hand, deckAfterOneHand) = Hand.deal(deck)

	println("hand = " + hand)
	println("deckAfterOneHand = " + deckAfterOneHand)
}
