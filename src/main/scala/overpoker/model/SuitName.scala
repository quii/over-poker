package overpoker.model

sealed trait SuitName

case object Hearts extends SuitName
case object Diamonds extends SuitName
case object Spades extends SuitName
case object Clubs extends SuitName
