package model.texasholdem.probability.prediction

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.playingcards.Rank._
import overpoker.playingcards._
import overpoker.texasholdem.prediction.PossibleHands


class PossibleHandTest extends FunSpec {

  describe("All possible hands") {
    it("contains 52 choose 5 hands") {
      val fiftyTwoChooseFive = 2598960
      PossibleHands.allHands.size should be(fiftyTwoChooseFive)
    }
  }

  describe("Possible hands before the flop") {

    it("contains ALL hands that contain player card 1 or player card 2 (including both card 1 and 2)") {
      val playerHand = PlayerHand(10 of Spades, 10 of Diamonds)
      val communityCards = Vector.empty
      val possibleHands = PossibleHands.getPossibleHands(playerHand, communityCards)

      possibleHands.par.foreach { hand =>
        hand should contain atLeastOneOf(playerHand.card1, playerHand.card2)
      }

      val allOtherHands: Set[Set[Card]] = allHands.diff(possibleHands)
      allOtherHands.par.foreach { hand =>
        hand should not contain atLeastOneOf(playerHand.card1, playerHand.card2)
      }
    }
  }

  describe("Possible hands after the flop and before the turn") {
    def contains_both_player_cards_and_at_least_1_comm_card(playerHand: PlayerHand, communityCards: Vector[Card], hand: Set[Card]): Boolean = {
      // Rule1: Hand must contain player card 1 AND 2 and AT LEAST 1 community card
      if ((hand.contains(playerHand.card1) && hand.contains(playerHand.card2)) && hand.intersect(communityCards.toSet).nonEmpty) true
      else false
    }

    def contains_at_least_1_player_card_and_at_least_2_community_cards(playerHand: PlayerHand, communityCards: Vector[Card], hand: Set[Card]): Boolean = {
      // Rule 2: Hand must contain player card 1 OR 2 and AT LEAST 2 community cards
      if ((hand.contains(playerHand.card1) || hand.contains(playerHand.card2)) && (hand.intersect(communityCards.toSet).size >= 2)) true
      else false
    }

    it("contains only hands that conform to Rule 1: both player cards and at least 1 x community card or Rule 2: at least 1 player card and at least 2 x community cards") {
      val playerHand = PlayerHand(Ace of Spades, 10 of Spades)
      val communityCards = Vector(6 of Hearts, 10 of Diamonds, 8 of Hearts)

      val possibleHands = PossibleHands.getPossibleHands(playerHand, communityCards)
      allHands.par.foreach { hand =>
        if (contains_both_player_cards_and_at_least_1_comm_card(playerHand, communityCards, hand) || contains_at_least_1_player_card_and_at_least_2_community_cards(playerHand, communityCards, hand))
          possibleHands should contain(hand)
        else
          possibleHands should not contain hand
      }
    }
  }

  describe("Possible hands after the turn and before the river") {
    def contains_both_player_cards_and_at_least_two_community_cards(playerHand: PlayerHand, communityCards: Vector[Card], hand: Set[Card]): Boolean = {
      (hand.contains(playerHand.card1) && hand.contains(playerHand.card2)) && (hand.intersect(communityCards.toSet).size >= 2)
    }
    def contains_at_least_one_player_card_and_at_least_three_community_cards(playerHand: PlayerHand, communityCards: Vector[Card], hand: Set[Card]): Boolean = {
      (hand.contains(playerHand.card1) || hand.contains(playerHand.card2)) && (hand.intersect(communityCards.toSet).size >= 3)
    }

    it("contains only hands that conform to Rule 1: contains both player cards and AT LEAST 2 community cards OR Rule2: contain player card 1 OR 2 and AT LEAST 3 community cards") {
      val playerHand = PlayerHand(Ace of Spades, 10 of Spades)
      val communityCards = Vector(6 of Hearts, 10 of Diamonds, 8 of Hearts, Queen of Clubs)

      val possibleHands = PossibleHands.getPossibleHands(playerHand, communityCards)
      allHands.par.foreach { hand =>
        if (contains_both_player_cards_and_at_least_two_community_cards(playerHand, communityCards, hand) || contains_at_least_one_player_card_and_at_least_three_community_cards(playerHand, communityCards, hand))
          possibleHands should contain(hand)
        else
          possibleHands should not contain hand
      }
    }
  }

  describe("Possible hands after the river") {
    it("returns any empty set") {
      val playerHand = PlayerHand(Ace of Spades, Ace of Diamonds)
      val communityCards = Vector(2 of Clubs, 3 of Clubs, 4 of Clubs, 5 of Clubs, 6 of Clubs)
      PossibleHands.getPossibleHands(playerHand, communityCards) should be(Set(Set.empty))
    }
  }

  private lazy val allHands = PossibleHands.allHands
}
