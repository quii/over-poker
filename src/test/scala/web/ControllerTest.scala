package web

import org.http4s.Status._
import org.http4s._
import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.playingcards._
import overpoker.texasholdem.hands.{HighCard, Flush, Hand}
import overpoker.web.parsing.PlayingCardCodecs.HandRequest
import overpoker.web.{Controller, HandIdentityService}
import org.http4s.argonaut._
import _root_.argonaut._, Argonaut._
import overpoker.playingcards.Rank._

class ControllerTest extends FunSpec {

  import Helpers._

  it("calls the service layer to tell you the best hand you have"){

    val expectedHand = HighCard(Ace)
    val testIdentityService = new TestIdentityService(expectedHand)
    val controller = Controller.getHand(testIdentityService)

    val handRequest = HandRequest(Vector(Ace of Spades, King of Hearts), Vector(10 of Diamonds, 9 of Diamonds, 8 of Diamonds))

    val request = Request(
      uri=Uri(path = s"/hand"),
      method = Method.POST
    ).withBody(handRequest.asJson)

    val resp: Response = controller.run(request.run).run

    resp.status should be(Ok)
    resp.body.asString should be({"""{"hand":"HighCard(Ace)"}"""})

  }
}

class TestIdentityService(expectedHand: Hand) extends HandIdentityService{
  override def identifyHand(playerHand: PlayerHand, communityCards: Vector[Card]): Hand = expectedHand
}

