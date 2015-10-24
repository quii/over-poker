package overpoker.texasholdem.prediction

import overpoker.playingcards._


object PossibleHands {

  val allHands: Set[Set[Card]] = {
    Deck.fullDeck.cards.combinations(5).map((x: Vector[Card]) => x.toSet).toSet
  }

}
