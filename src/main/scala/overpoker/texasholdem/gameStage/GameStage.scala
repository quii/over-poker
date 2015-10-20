package overpoker.texasholdem.gameStage

import scala.language.implicitConversions
import overpoker.playingcards.Card


sealed trait GameStage

case object Flop extends GameStage {
  implicit def toVector(flop: Flop): Vector[Card] = Vector(flop.card1,flop.card2,flop.card3)
}
case class Flop(card1: Card, card2: Card, card3: Card)

case object Turn extends GameStage {
  implicit def asVector(turn: Turn): Vector[Card] = Vector(turn.card1,turn.card2,turn.card3,turn.card4)
}
case class Turn(card1: Card, card2: Card, card3: Card, card4: Card)

case object River extends GameStage {
  implicit def toVector(river: River): Vector[Card] = Vector(river.card1,river.card2,river.card3,river.card4,river.card5)
}
case class River(card1: Card, card2: Card, card3: Card, card4: Card, card5: Card)




