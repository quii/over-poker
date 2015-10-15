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
  it("should parse cards JSON into a HandRequest"){

    val json =
      """
        |{
        | "player":{
        |   "Ace":"Spades",
        |   "Ace":"Hearts"
        | },
        | "community":{
        |   "10": "Clubs",
        |   "King": "Diamonds",
        |   "3":"Hearts
        | }
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
