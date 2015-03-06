package overpoker.texasholdem

import overpoker.playingcards.{Suit, Card, Deck, Hand}

import scala.collection.immutable.Iterable

case class Prediction(value: HandValue, percentageChance: Double){
  override def toString = s"$percentageChance% chance of $value"
}

object Prediction {

  def oddsOfPair(hand: Hand, communityCards: Vector[Card]): Vector[Prediction] = {
    val seenCards = communityCards :+ hand.card1 :+hand.card2
    val chanceOfMakingPair = chanceOfCardInDeck(hand.card1, unseenCards(seenCards.toSet))

    Vector(
      Prediction(Pair(hand.card1.rank), chanceOfMakingPair),
      Prediction(Pair(hand.card2.rank), chanceOfMakingPair)
    )
  }

  //http://poker.stackexchange.com/questions/1/what-are-the-odds-i-will-hit-my-flush
  def oddsOfFlush(hand: Hand, communityCards: Vector[Card]): Vector[Prediction] = {
    val seenCards = communityCards :+ hand.card1 :+hand.card2

    val potentialFlush = seenCards.groupBy(c=> c.suit).collect({
      case (suit, Vector(card1, card2, card3, card4)) => Vector(card1, card2, card3, card4)
    }).headOption

    potentialFlush.map{ flushySuit =>
      val outs = 9
      val chance = probability(outs, unseenCards(seenCards.toSet).size)
      Vector(Prediction(Flush(flushySuit.sortBy(x=> -x.rank.toInt).head.rank), chance))
    }.getOrElse(Vector.empty)
  }

  def oddsOfFullHouse(hand: Hand, communityCards: Vector[Card]): Vector[Prediction] = {
    val seenCards = communityCards :+ hand.card1 :+hand.card2

    val hasTwoPairs = HandValue.getValues(hand, communityCards).collect{case TwoPair(x, y) => TwoPair(x, y)}.headOption

    hasTwoPairs.map{ twoPairs =>
      val outForOneCard = 2
      val chance = probability(outForOneCard, unseenCards(seenCards.toSet).size)
      Vector(
        Prediction(FullHouse(twoPairs.highest, twoPairs.lowest), chance),
        Prediction(FullHouse(twoPairs.lowest, twoPairs.highest), chance)
      )
    }.getOrElse(Vector.empty)

  }

  private def chanceOfCardInDeck(card: Card, cards: Set[Card]): Double ={
    val outs = cards.count(c=> c.rank==card.rank)
    probability(outs, cards.size)
  }

  private def probability(outs: Int, totalCards: Int) = "%.1f".format((outs.toDouble / totalCards.toDouble) * 100).toDouble

  private def unseenCards(seen: Set[Card]) = Deck.fullDeck.cards.toSet -- seen

}
