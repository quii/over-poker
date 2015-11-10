package offline

import offline.parked.ProbabilitiesParked
import org.scalatest._
import matchers._
import overpoker.playingcards.PlayerHand

trait ProbabilityMatchers {

  class ProbabilityContainsPlayerHandMatcher(expectedPlayerHands: Vector[PlayerHand]) extends Matcher[Vector[ProbabilitiesParked]] {
    override def apply(left: Vector[ProbabilitiesParked]): MatchResult = {
      
      def probabilitiesContainsPlayerHand(ph: PlayerHand): Boolean = left.exists(_.playerHand == ph)
      
      val result = expectedPlayerHands.forall(probabilitiesContainsPlayerHand)
      MatchResult(
        result, 
        "Expected: Vector of Probabilities to contain all player hands, but it didn't",
        "Expected: Vector of Probabilities NOT to contain all player hands, but it did")
    }
  }
  def containAll(expectedPlayerHands: Vector[PlayerHand]) = new ProbabilityContainsPlayerHandMatcher(expectedPlayerHands)
}
