package overpoker.model

import scala.util.Random

trait Randomiser{
  def random(size: Int): Int
}

object DefaultRandomiser{
  implicit def reallyRandom = new Randomiser {
    override def random(size: Int): Int = {
      val rnd = new Random
      rnd.nextInt(size)
    }
  }
}