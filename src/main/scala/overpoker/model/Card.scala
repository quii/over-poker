package overpoker.model

case class Card (rank: Rank, suit: Suit){
  override def toString() = s"$rank of $suit"
}