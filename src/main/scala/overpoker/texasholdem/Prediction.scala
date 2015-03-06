package overpoker.texasholdem

import overpoker.playingcards.{Card, Deck, Hand}

case class Prediction(value: HandValue, percentageChance: Double)

object Prediction {

  def oddsOfPair(hand: Hand, communityCards: Vector[Card]): Vector[Prediction] = {
    val allCards = communityCards :+ hand.card1 :+hand.card2
    val unseenCards = Deck.fullDeck.cards.toSet -- allCards.toSet

    val chanceOfCard = chanceOfCardInDeck(hand.card1, unseenCards)

    Vector(
      Prediction(Pair(hand.card1.rank), chanceOfCard),
      Prediction(Pair(hand.card2.rank), chanceOfCard)
    )
  }

  def chanceOfCardInDeck(card: Card, cards: Set[Card]): Double ={
    val outs = cards.count(c=> c.rank==card.rank)
    (cards.size/outs)
  }

}
