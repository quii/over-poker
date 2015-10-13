package overpoker.web

import org.http4s.server._
import org.http4s.dsl._

object Controller {
  val hello = HttpService {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name")
  }
}