package web.parsing

import argonaut.Argonaut._
import argonaut.{Parse, _}
import org.scalatest.Tag
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import overpoker.playingcards.Rank._
import overpoker.playingcards._


class PlayingCardCodecsTest extends AnyFunSpec {

  import overpoker.web.parsing.PlayingCardCodecs._

  it("should parse cards JSON into a HandRequest", Tag("cj")){

    val json =
      """{
        |  "player": [
        |    {
        |      "rank": "Ace",
        |      "suit": "♠"
        |    },
        |    {
        |      "rank": "Ace",
        |      "suit": "♥"
        |    }
        |  ],
        |  "community": [
        |    {
        |      "rank": "10",
        |      "suit": "♣"
        |    },
        |    {
        |      "rank": "King",
        |      "suit": "♦"
        |    },
        |    {
        |      "rank": "3",
        |      "suit": "♥"
        |    }
        |  ]
        |}
      """.stripMargin

    val result: Either[String,HandRequest] =
      Parse.decodeEither[HandRequest](json)

    val expected = HandRequest(
      Vector(Ace of Spades, Ace of Hearts),
      Vector(10 of Clubs, King of Diamonds, 3 of Hearts)
    )

    result should be(HandRequest)

  }
}
