package overpoker

import org.http4s.server.blaze.BlazeBuilder
import overpoker.web.Controller

object WebServer extends App {
  BlazeBuilder.bindHttp(8080)
    .mountService(Controller.hello, "/")
    .run
    .awaitShutdown()
}