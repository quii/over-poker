package probability.checkers

import overpoker.playingcards._
import overpoker.texasholdem.hands._

case class GameResults(playerHand: Hand, flop: Hand, turn: Hand, river: Hand)
case class Result(handName: String, numberOfOccurrences: Int, probability: Double)
case class AnalysedResults(handResults: List[Result], flopResults: List[Result], turnResults: List[Result], riverResults: List[Result])

object MonteCarloSimulation {

  def runMonteCarlo(iterations: Int): List[GameResults] = {

    def oneIteration: Option[GameResults] = {
      val deck = Deck.fullDeck

      // Add five players
      val (_, newDeck) = PlayerHand.deal(deck)
      val (_, newDeck2) = PlayerHand.deal(newDeck)
      val (_, newDeck3) = PlayerHand.deal(newDeck2)
      val (_, newDeck4) = PlayerHand.deal(newDeck3)

      // Deal out to the player we care about
      val (hand, deckAfterOneHand) = PlayerHand.deal(newDeck4)
      val playerHand = Hand.getValues(hand, Vector())

      // Ignore burning a card for now
      //      val (_, deckAfterBurn) = deckAfterOneHand.drawCardAt(0)

      // Remove poket pairs from the simulation
      //      if (Hand.getValues(hand, Vector()).head.isInstanceOf[Pair]) return None

      val (flopCards, deckAfterFlop) = deckAfterOneHand.flop
      val handsAfterFlop = Hand.getValues(hand, flopCards)

      val (turnCard, deckAfterTurn) = deckAfterFlop.turn
      val handsAfterTurn = Hand.getValues(hand, flopCards ++ turnCard)

      val (riverCard, deckAfterRiver) = deckAfterTurn.river
      val handsAfterRiver = Hand.getValues(hand, flopCards ++ turnCard ++ riverCard)

      // Relies on the order of the hands being returned best to worst
      Some(GameResults(playerHand.head, handsAfterFlop.head, handsAfterTurn.head, handsAfterRiver.head))
    }

    val results = for (_ <- 1 to iterations; result = oneIteration) yield result
    results.flatten.toList
  }

  def main(args: Array[String]) {
    val results = runMonteCarlo(10000000)
    
    val analysedResults: AnalysedResults = analyseGameResults(results)

    printResults(analysedResults)

  }

  implicit val randomDraw = DefaultRandomiser.reallyRandom

  def analyseGameResults(results: List[GameResults]): AnalysedResults = {

    def analyseStage(stageResults: List[Hand]): List[Result] = {

      val numResults = stageResults.length.toDouble

      val handResults: List[(String, Int)] = List(
        ("High Card      ", stageResults.count(_.isInstanceOf[HighCard])),
        ("Pair           ", stageResults.count(_.isInstanceOf[Pair])),
        ("Two Pair       ", stageResults.count(_.isInstanceOf[TwoPair])),
        ("Three of A Kind", stageResults.count(_.isInstanceOf[ThreeOfAKind])),
        ("Straight       ", stageResults.count(_.isInstanceOf[Straight])),
        ("Flush          ", stageResults.count(_.isInstanceOf[Flush])),
        ("Full House     ", stageResults.count(_.isInstanceOf[FullHouse])),
        ("Four of A Kind ", stageResults.count(_.isInstanceOf[FourOfAKind])),
        ("Straight Flush ", stageResults.count(_.isInstanceOf[StraightFlush])),
        ("Royal Flush    ", stageResults.count(_.equals(RoyalFlush)))
      )

      handResults.map(result => Result(result._1, result._2, result._2 / numResults))

    }

    val handResults = analyseStage(results.map(_.playerHand))
    val flopResults = analyseStage(results.map(_.flop))
    val turnResults = analyseStage(results.map(_.turn))
    val riverResults = analyseStage(results.map(_.river))

    AnalysedResults(handResults, flopResults, turnResults, riverResults)
  }

  def printResults(analysedResults: AnalysedResults) = {

    val tabs: String = "\t\t\t\t\t"

    def printResult(results: List[Result]): Unit = {
      results.foreach(result =>
        println(s"${result.handName}: \t${result.numberOfOccurrences} ${" " * (15 - result.numberOfOccurrences.toString.length)}${result.probability}")
      )
    }
    println(s"|  Number of $tabs|\tChance of being best hand...  |")
    println("==================================================================")
    println(s"After the deal$tabs|")
    println("------------------------------------------------------------------")
    printResult(analysedResults.handResults)
    println()

    println(s"After the Flop$tabs|")
    println("------------------------------------------------------------------")
    printResult(analysedResults.flopResults)
    println()

    println(s"After the Turn$tabs|")
    println("------------------------------------------------------------------")
    printResult(analysedResults.turnResults)
    println()

    println(s"After the River$tabs|")
    println("------------------------------------------------------------------")
    printResult(analysedResults.riverResults)
  }
}

// Questions
// Is the random number generator good enough?
// Does it matter that the dealing does two cards at a time?
// Does it matter that there isn't a card being burnt?

// 10,000,000 iterations
//
//|  Number of 					  |	Chance of being best hand...  |
//==================================================================
//After the deal					|
//------------------------------------------------------------------
//High Card      : 	9412596         0.9412596
//Pair           : 	587404          0.0587404
//Two Pair       : 	0               0.0
//Three of A Kind: 	0               0.0
//Straight       : 	0               0.0
//Flush          : 	0               0.0
//Full House     : 	0               0.0
//Four of A Kind : 	0               0.0
//Straight Flush : 	0               0.0
//Royal Flush    : 	0               0.0
//
//After the Flop					|
//------------------------------------------------------------------
//High Card      : 	5011663         0.5011663
//Pair           : 	4226814         0.4226814
//Two Pair       : 	475310          0.047531
//Three of A Kind: 	210690          0.021069
//Straight       : 	39023           0.0039023
//Flush          : 	19616           0.0019616
//Full House     : 	14358           0.0014358
//Four of A Kind : 	2377            2.377E-4
//Straight Flush : 	133             1.33E-5
//Royal Flush    : 	16              1.6E-6
//
//After the Turn					|
//------------------------------------------------------------------
//High Card      : 	3248290         0.324829
//Pair           : 	4832147         0.4832147
//Two Pair       : 	1214198         0.1214198
//Three of A Kind: 	359888          0.0359888
//Straight       : 	154995          0.0154995
//Flush          : 	99522           0.0099522
//Full House     : 	81132           0.0081132
//Four of A Kind : 	6994            6.994E-4
//Straight Flush : 	2548            2.548E-4
//Royal Flush    : 	286             2.86E-5
//
//After the River					|
//------------------------------------------------------------------
//High Card      : 	1741836         0.1741836
//Pair           : 	4655646         0.4655646
//Two Pair       : 	2182488         0.2182488
//Three of A Kind: 	488957          0.0488957
//Straight       : 	352969          0.0352969
//Flush          : 	288863          0.0288863
//Full House     : 	255741          0.0255741
//Four of A Kind : 	16421           0.0016421
//Straight Flush : 	15403           0.0015403
//Royal Flush    : 	1676            1.676E-4





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
