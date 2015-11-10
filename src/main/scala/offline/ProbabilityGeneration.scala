package offline

import java.io.File
import scala.util.Try

object ProbabilityGeneration {

  def main(args: Array[String]) {
    val dataDirPath = args.headOption.getOrElse("../data")
    val numberOfCardsInDeck = Try(args(1)).getOrElse("52").toInt

    val dataDir = new File(dataDirPath)
    dataDir.mkdirs()

    // Get the probabilities for each possible player hand before the flop
      // on the flop
      // on the turn
      // on the river

    // write each situation to a separate file in the data dir
  }
}
