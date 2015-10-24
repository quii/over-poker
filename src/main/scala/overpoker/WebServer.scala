package overpoker

import org.http4s.server.blaze.BlazeBuilder
import overpoker.web.{TexasHoldemHandIdentityServuce, Controller}

object WebServer extends App {
  
  val helloController = Controller.getHand(TexasHoldemHandIdentityServuce)
  
  BlazeBuilder.bindHttp(8080)
    .mountService(helloController, "/")
    .run
    .awaitShutdown()
}