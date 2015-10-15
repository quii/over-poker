package overpoker.web

import org.http4s.EntityDecoder
import org.http4s.server._
import org.http4s.dsl._
import overpoker.playingcards.{Ace, PlayerHand, Card}
import overpoker.texasholdem.hands.{Flush, Hand}
import org.http4s.argonaut._
import _root_.argonaut._, Argonaut._
import overpoker.web.parsing.PlayingCardCodecs.HandRequest

trait HandIdentityService{
  def identifyHand(playerHand: PlayerHand, communityCards: Vector[Card]): Hand
}



object TexasHoldemHandIdentityServuce extends HandIdentityService{
  override def identifyHand(playerHand: PlayerHand, cards: Vector[Card]): Hand = Flush(Ace)
}

object Controller {

  import parsing.PlayingCardCodecs._
  implicit def myJsonA: EntityDecoder[HandRequest] = jsonOf[HandRequest]

  def getHand(service: HandIdentityService) = HttpService {
    case request@ POST -> Root / "hand" =>
      request.as[HandRequest].
        flatMap(cards =>
          Ok(jSingleObject("hand", jString(service.identifyHand(PlayerHand(cards.player(0), cards.player(1)), cards.community).toString))))

  }
}