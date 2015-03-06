package overpoker.texasholdem

import overpoker.playingcards.Card

object Turn{
  implicit def asVector(turn: Turn): Vector[Card] = Vector(turn.card1,turn.card2,turn.card3,turn.card4)
}
case class Turn(card1: Card, card2: Card, card3: Card, card4: Card)
