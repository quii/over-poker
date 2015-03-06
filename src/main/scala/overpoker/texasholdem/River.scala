package overpoker.texasholdem

import overpoker.playingcards.Card

object River{
  implicit def toVector(river: River) = Vector(river.card1,river.card2,river.card3,river.card4,river.card5)
}

case class River(card1: Card, card2: Card, card3: Card, card4: Card, card5: Card)
