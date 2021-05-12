package web

import org.http4s.Status._
import org.http4s._
import overpoker.playingcards._
import overpoker.texasholdem.hands.{Flush, Hand, HighCard}
import overpoker.web.parsing.PlayingCardCodecs.HandRequest
import overpoker.web.{Controller, HandIdentityService}
import cats.effect.IO
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.EncoderOps
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import overpoker.playingcards.Rank._

class ControllerTest extends AnyFunSpec {

  import Helpers._

  it("calls the service layer to tell you the best hand you have"){

    implicit val suitDecoder: Encoder[Suit] = deriveEncoder[Suit]
    implicit val rankDecoder: Encoder[Rank] = deriveEncoder[Rank]
    implicit val cardDecoder: Encoder[Card] = deriveEncoder[Card]
    implicit val decoder: Encoder[HandRequest] = deriveEncoder[HandRequest]

    val expectedHand = HighCard(Ace, King)
    val testIdentityService = new TestIdentityService(expectedHand)
    val controller: HttpRoutes[IO] = Controller.getHand(testIdentityService)

    val handRequest = HandRequest(Vector(Ace of Spades, King of Hearts), Vector(10 of Diamonds, 9 of Diamonds, 8 of Diamonds))

    val request: Request[IO] = Request(
      uri=Uri(path = s"/hand"),
      method = Method.POST
    ).withEntity(handRequest.asJson)

    val resp: Response[IO] = controller(request).value.unsafeRunSync().get

    resp.status should be(Ok)
    resp.body.asString should be({"""{"hand":"HighCard(Ace,King)"}"""})

  }
}

class TestIdentityService(expectedHand: Hand) extends HandIdentityService{
  override def identifyHand(playerHand: PlayerHand, communityCards: Vector[Card]): Hand = expectedHand
}

