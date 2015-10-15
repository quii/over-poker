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

  val controller = Controller.getHand(TestHelloService)

  it("says your name right back at you"){
    val handRequest = HandRequest(Vector(Ace of Spades, King of Hearts), Vector(10 of Diamonds, 9 of Diamonds, 8 of Diamonds))

    val request = Request(
      uri=Uri(path = s"/hand"),
      method = Method.POST
    ).withBody("foo")

    val resp: Response = controller.run(request.run).run

    resp.status should be(Ok)
    resp.body.asString should be({"""{"hand":"HighCard(Ace,King)"}"""})

  }
}

object TestHelloService extends HandIdentityService{
  //todo: Bit weak, just return high card of whatever the player has, should make instance of this stub which return what we want it to
  override def identifyHand(playerHand: PlayerHand, communityCards: Vector[Card]): Hand =
    HighCard(playerHand.card1.rank, playerHand.card2.rank)
}

