package web.parsing

import argonaut.Argonaut._
import argonaut.{Parse, _}
import org.scalatest.{Tag, FunSpec}
import org.scalatest.Matchers._
import org.typelevel.scalatest.DisjunctionMatchers
import overpoker.playingcards.Rank._
import overpoker.playingcards._

import scalaz.\/

class PlayingCardCodecsTest extends FunSpec with DisjunctionMatchers {

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

    val result: String \/ HandRequest =
      Parse.decodeEither[HandRequest](json)

    val expected = HandRequest(
      Vector(Ace of Spades, Ace of Hearts),
      Vector(10 of Clubs, King of Diamonds, 3 of Hearts)
    )

    result should be(right[HandRequest])

  }
}
