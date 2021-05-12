package overpoker

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._
import overpoker.web.{Controller, TexasHoldemHandIdentityServuce}

import scala.concurrent.ExecutionContext.Implicits.global

object WebServer extends IOApp {

  val helloController = Controller.getHand(TexasHoldemHandIdentityServuce)

  val port = sys.env.get("PORT").map(_.toInt).getOrElse(8080)

  private val app = Router("/" -> helloController).orNotFound


  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global).bindHttp(port)
      .withHttpApp(app)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}