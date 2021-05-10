package overpoker.web

import cats.effect.IO
import io.circe.Json.JString
import io.circe.generic.semiauto
import io.circe.{Decoder, Json}
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.{circeEntityDecoder, circeEntityEncoder}
import org.http4s.dsl.io._
import overpoker.playingcards.{Card, PlayerHand}
import overpoker.texasholdem.hands.Hand

trait HandIdentityService {
  def identifyHand(playerHand: PlayerHand, communityCards: Vector[Card]): Hand
}

object TexasHoldemHandIdentityServuce extends HandIdentityService {
  override def identifyHand(playerHand: PlayerHand, cards: Vector[Card]): Hand = Hand.getValues(playerHand, cards).head
}

object Controller {

  import parsing.PlayingCardCodecs._

  implicit def decoder: Decoder[HandRequest] = semiauto.deriveDecoder[HandRequest]

  def getHand(service: HandIdentityService) = HttpRoutes.of[IO] {
    case request@POST -> Root / "hand" =>
      for {
        cards <- request.as[HandRequest]
        json = Json.obj(("hand", JString(service.identifyHand(PlayerHand(cards.player(0), cards.player(1)), cards.community).toString)))
        resp <- Ok(json)
      } yield resp
  }
}