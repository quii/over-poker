package overpoker.texasholdem.prediction

import overpoker.playingcards._


object PossibleHands {
  lazy val allHands: Set[Set[Card]] = {
    Deck.fullDeck.cards.combinations(5).map((x: Vector[Card]) => x.toSet).toSet
  }

  def afterFlop(communityCards: Vector[Card]): Boolean = communityCards.length >= 3
  def beforeTurn(communityCards: Vector[Card]): Boolean = communityCards.length < 4
  def afterTurn(communityCards: Vector[Card]): Boolean = communityCards.length >= 4
  def afterRiver(communityCards: Vector[Card]): Boolean = communityCards.length >= 5

  def conformsToBeforeFlopRules(playerHand: PlayerHand, hand: Set[Card]): Boolean = {
    hand.contains(playerHand.card1) || hand.contains(playerHand.card2)
  }

  def conformsToAfterFlopBeforeTurnRules(playerHand: PlayerHand, communityCards: Vector[Card], hand: Set[Card]): Boolean = {
    val ruleOne = hand.contains(playerHand.card1) && hand.contains(playerHand.card2) && hand.intersect(communityCards.toSet).nonEmpty
    val ruleTwo = (hand.contains(playerHand.card1) || hand.contains(playerHand.card2)) && (hand.intersect(communityCards.toSet).size >= 2)

    ruleOne || ruleTwo
  }

  def conformsToAfterTurnRules(playerHand: PlayerHand, communityCards: Vector[Card], hand: Set[Card]): Boolean = {
    val ruleOne = hand.contains(playerHand.card1) && hand.contains(playerHand.card2) && (hand.intersect(communityCards.toSet).size >= 2)
    val ruleTwo = (hand.contains(playerHand.card1) || hand.contains(playerHand.card2)) && (hand.intersect(communityCards.toSet).size >= 3)

    ruleOne || ruleTwo
  }

  def getPossibleHands(playerHand: PlayerHand, communityCards: Vector[Card]): Set[Set[Card]] = {
    if (afterRiver(communityCards))
      Set(Set.empty)
    else if (afterTurn(communityCards))
      allHands.filter(conformsToAfterTurnRules(playerHand, communityCards, _))
    else if (afterFlop(communityCards) && beforeTurn(communityCards))
      allHands.filter(conformsToAfterFlopBeforeTurnRules(playerHand, communityCards, _))
    else
      allHands.filter(conformsToBeforeFlopRules(playerHand, _))
  }
}


