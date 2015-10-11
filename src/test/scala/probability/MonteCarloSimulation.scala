package probability

import overpoker.playingcards._
import overpoker.texasholdem.hands._

import scala.collection.immutable.IndexedSeq

case class Result(flop: Hand, turn: Hand, river: Hand)

object MonteCarloSimulation {

  def runMonteCarlo(iterations: Int): List[Result] = {

    def oneIteration: Option[Result] = {
      val deck = Deck.fullDeck

      // Add five players
      val (_, newDeck) = PlayerHand.deal(deck)
      val (_, newDeck2) = PlayerHand.deal(newDeck)
      val (_, newDeck3) = PlayerHand.deal(newDeck2)
      val (_, newDeck4) = PlayerHand.deal(newDeck3)

      val (hand, deckAfterOneHand) = PlayerHand.deal(newDeck4)
      val (_, deckAfterBurn) = deckAfterOneHand.drawCardAt(0)
      // Remove poket pairs from the simulation
      //      if (Hand.getValues(hand, Vector()).head.isInstanceOf[Pair]) return None

      val (flopCards, deckAfterFlop) = deckAfterBurn.flop
      val handsAfterFlop = Hand.getValues(hand, flopCards)

      val (turnCard, deckAfterTurn) = deckAfterFlop.turn
      val handsAfterTurn = Hand.getValues(hand, flopCards ++ turnCard)

      val (riverCard, deckAfterRiver) = deckAfterTurn.river
      val handsAfterRiver = Hand.getValues(hand, flopCards ++ turnCard ++ riverCard)

      // Relies on the order of the hands being returned best to worst
      Some(Result(handsAfterFlop.head, handsAfterTurn.head, handsAfterRiver.head))
    }

    val results: IndexedSeq[Option[Result]] = for (_ <- 1 to iterations; result = oneIteration) yield result
    results.flatten.toList
  }

  def main(args: Array[String]) {
    val results = runMonteCarlo(50000)
    val numResults = results.length.toDouble

    val numHighCards = numberOfHighCardInFlop(results)
    val chanceHighCard = numHighCards / numResults

    val numPairs = numberOfPairsInFlop(results)
    val chanceOfPair = numPairs / numResults

    printResults(numHighCards, chanceHighCard, numPairs, chanceOfPair)
  }

  implicit val randomDraw = DefaultRandomiser.reallyRandom

  def printResults(numOfHighCardsInFLop: Int,
                   chanceOfHighCard: Double,
                   numberOfPairsInFlop: Int,
                   chanceOfPair: Double) {
    println("After the Flop:")
    println(s"Number of High Cards: \t$numOfHighCardsInFLop \tChance of High Card: \t$chanceOfHighCard")
    println(s"Number of Pairs: \t\t$numberOfPairsInFlop \tChance of Pair: \t\t$chanceOfPair")
  }

  def numberOfPairsInFlop(results: List[Result]) = results.count(result => result.flop.isInstanceOf[Pair])

  //  def numberOfHighCardInFlop(results: List[Result]) = results.count(result => result.flop.isInstanceOf[HighCard])
  def numberOfHighCardInFlop(results: List[Result]) = results.count { (result) => result.flop match {
      case HighCard(_, _) => true
      case _ => false
    }
  }
}

//After the Flop, remove pocket pairs (around 50k iterations):
//Number of High Cards: 	25108 	Chance of High Card: 	0.5328636006706424
//Number of Pairs: 		    19014 	Chance of Pair: 		  0.40353148411468837

// 500k iterations
//After the Flop:
//Number of High Cards: 	249777 	Chance of High Card: 	0.499554
//Number of Pairs: 	  	  211826 	Chance of Pair: 	  	0.423652

// 500k ish iterations, remove pocket pairs:
//After the Flop:
//Number of High Cards: 	250019 	Chance of High Card: 	0.5311856650257818
//Number of Pairs: 		    190800 	Chance of Pair: 		  0.40537009142072866
