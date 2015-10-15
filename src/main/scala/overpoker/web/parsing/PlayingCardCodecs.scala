package overpoker.web.parsing

import argonaut.CodecJson
import overpoker.playingcards.Card

object PlayingCardCodecs {

  case class HandRequest(player: Vector[Card], community: Vector[Card])

  implicit def PersonCodecJson: CodecJson[HandRequest] = ???
}
