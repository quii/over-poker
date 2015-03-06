package overpoker.texasholdem

import overpoker.playingcards.Card

object Flop{
  implicit def toVector(flop: Flop) = Vector(flop.card1,flop.card2,flop.card3)
}

case class Flop(card1: Card, card2: Card, card3: Card)
