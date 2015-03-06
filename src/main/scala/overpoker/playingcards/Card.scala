package overpoker.playingcards

case class Card (rank: Rank, suit: Suit){
  override def toString() = s"$rank of $suit"
}