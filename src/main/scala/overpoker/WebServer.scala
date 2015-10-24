package overpoker

import org.http4s.server.blaze.BlazeBuilder
import overpoker.web.{TexasHoldemHandIdentityServuce, Controller}

object WebServer extends App {
  
  val helloController = Controller.getHand(TexasHoldemHandIdentityServuce)

  val port = sys.env.get("PORT").map(_.toInt).getOrElse(8080)

  BlazeBuilder.bindHttp(port)
    .mountService(helloController, "/")
    .run
    .awaitShutdown()
}