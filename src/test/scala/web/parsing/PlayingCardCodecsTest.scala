package web.parsing

import argonaut.Parse
import org.scalatest.FunSpec
import org.scalatest.Matchers._
import org.typelevel.scalatest.DisjunctionMatchers
import overpoker.playingcards._
import overpoker.web.parsing.PlayingCardCodecs.HandRequest
import overpoker.playingcards.Rank._

import scalaz.\/

class PlayingCardCodecsTest extends FunSpec with DisjunctionMatchers {

  import overpoker.web.parsing.PlayingCardCodecs._

  it("should parse cards JSON into a HandRequest"){

    val json =
      """{
        |  "player": [
        |    {
        |      "rank": "Ace",
        |      "suit": "Spades"
        |    },
        |    {
        |      "rank": "Ace",
        |      "suit": "Hearts"
        |    }
        |  ],
        |  "community": [
        |    {
        |      "rank": "10",
        |      "suit": "Clubs"
        |    },
        |    {
        |      "rank": "King",
        |      "suit": "Diamonds"
        |    },
        |    {
        |      "rank": "3",
        |      "suit": "Hearts"
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
