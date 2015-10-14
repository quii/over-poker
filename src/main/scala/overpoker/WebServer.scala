package overpoker

import org.http4s.server.blaze.BlazeBuilder
import overpoker.web.{RealHelloService, Controller}

object WebServer extends App {
  
  val helloController = Controller.hello(RealHelloService)
  
  BlazeBuilder.bindHttp(8080)
    .mountService(helloController, "/")
    .run
    .awaitShutdown()
}