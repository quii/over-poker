package offline

import java.io.{FileWriter, File}
import prediction.Probability

import scala.language.reflectiveCalls
import scala.util.Try

object ProbabilityGeneration {

  def main(args: Array[String]) {
    val dataDirPath = args.headOption.getOrElse("../data")
    val numberOfCardsInDeck = Try(args(1)).getOrElse("52").toInt

    val dataDir = new File(dataDirPath)
    dataDir.mkdirs()

    val flopProbabilities = Probability.preFlop_flop(numberOfCardsInDeck)

    val turnProbabilities = Probability.preFlop_turn(numberOfCardsInDeck)

    val riverProbabilities = Probability.preFlop_river(numberOfCardsInDeck)

    writeToFile(dataDirPath + "/pre-flop-flop.data", csv(flopProbabilities))
    writeToFile(dataDirPath + "/pre-flop-turn.data", csv(turnProbabilities))
    writeToFile(dataDirPath + "/pre-flop-river.data", csv(riverProbabilities))
  }

  private def using[A <: {def close(): Unit}, B](param: A)(f: A => B): B =
    try { f(param) } finally { param.close() }

  private def writeToFile(fileName:String, data:String) = using (new FileWriter(fileName)) { fileWriter => fileWriter.write(data) }

  private def csv(input: Any): String = {
    "something from main"
  }

}
