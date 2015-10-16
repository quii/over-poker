package overpoker.web.parsing

import argonaut._, Argonaut._
import overpoker.playingcards._

object PlayingCardCodecs {

  case class HandRequest(player: Vector[Card], community: Vector[Card])

  implicit def HandRequestCodecJson: CodecJson[HandRequest] = casecodec2(HandRequest.apply, HandRequest.unapply)("player", "community")
  implicit def CardCodecJson: CodecJson[Card] = casecodec2(Card.apply, Card.unapply)("rank", "suit")

  implicit def RankEncodeJson: EncodeJson[Rank] = EncodeJson((r: Rank) => jString(r.toString))
  implicit def RankDecodeJson: DecodeJson[Rank] = DecodeJson(c => for {
    rank <- c.focus.as[String]
  } yield rank match {
      case "Ace" => Ace
      case "King" => King
      case "Queen" => Queen
      case "Jack" => Jack
      case other if "\\d+".r.pattern.matcher(other).matches() && other.toInt > 1 && other.toInt < 11 => Numeric(other.toInt)
      case dunno => throw new RuntimeException(s"Don't understand rank '$dunno'")
    })

  implicit def SuitEncodeJson: EncodeJson[Suit] = EncodeJson((s: Suit) => jString(s.toString))
  implicit def SuitDecodeJson: DecodeJson[Suit] = DecodeJson(c => for {
     suit <- c.focus.as[String]
  } yield suit match {
      case "Hearts" => Hearts
      case "Diamonds" => Diamonds
      case "Spades" => Spades
      case "Clubs" => Clubs
      case dunno => throw new RuntimeException(s"Don't understand suit '$dunno'")
    })
}
