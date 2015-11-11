package offline.functional

import java.io.File
import offline.ProbabilityGeneration
import org.scalatest.FunSpec
import org.scalatest.Matchers._
import scala.io.Source

class OfflineProbabilityGenerationTest extends FunSpec {

  private val pathToDataDir = "../data"
  private def dataFiles = new File(pathToDataDir).listFiles

  TestSetup.init(pathToDataDir)

  describe("The offline probability data generation") {
    it("should create a file for pre-flop: flop") {
      dataFiles.exists(_.getName == "pre-flop-flop.data") should be(true)
    }

    it("should create a file for pre-flop: turn") {
      dataFiles.exists(_.getName == "pre-flop-turn.data") should be(true)
    }
    it("should create a file for pre-flop: river") {
      dataFiles.exists(_.getName == "pre-flop-river.data") should be(true)
    }

    it("should have populated the flop file with data") {
      val file = dataFiles.find(_.getName == "pre-flop-flop.data")
      val source = Source.fromFile(file.get.getAbsolutePath)
      val lines = try source.mkString finally source.close()
      lines should not be empty
    }

    it("should have populated the turn file with data") {
      val file = dataFiles.find(_.getName == "pre-flop-turn.data")
      val source = Source.fromFile(file.get.getAbsolutePath)
      val lines = try source.mkString finally source.close()
      lines should not be empty
    }

    it("should have populated the river file with data") {
      val file = dataFiles.find(_.getName == "pre-flop-river.data")
      val source = Source.fromFile(file.get.getAbsolutePath)
      val lines = try source.mkString finally source.close()
      lines should not be empty
    }
  }
}

object TestSetup {
  def init(pathToDir: String): Unit = {

    val dataDir: File = new File(pathToDir)
    if (dataDir.exists()) {
      dataDir.listFiles.foreach(f => f.delete)
      dataDir.delete
    }

    val pathToData = "../data"
    val numberOfCardsInDeck = "10"
    ProbabilityGeneration.main(Array(pathToData, numberOfCardsInDeck))
  }
}