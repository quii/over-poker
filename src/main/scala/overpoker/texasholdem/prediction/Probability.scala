package overpoker.texasholdem.prediction

import overpoker.playingcards.{Card, PlayerHand}
import overpoker.texasholdem.hands.Hand

case class ProbabilityModel(flop: Option[Map[Hand,Double]] = None, turn: Option[Map[Hand,Double]] = None, river: Option[Map[Hand,Double]] = None)
case class ProbabilityError(message: String = "")

object Probability {
  def probabilities(playerHand: PlayerHand, communityCards: Vector[Card]): Either[ProbabilityError, ProbabilityModel] = communityCards.length match {
    case 0 => Right(ProbabilityModel(flop = Some(Map.empty), turn = Some(Map.empty), river = Some(Map.empty)))
    case 3 => Right(ProbabilityModel(turn = Some(Map.empty), river = Some(Map.empty)))
    case 4 => Right(ProbabilityModel(river = Some(Map.empty)))
    case 5 => Right(ProbabilityModel())
    case _ => Left(ProbabilityError(message = "Wrong number of community cards..."))
  }
}
