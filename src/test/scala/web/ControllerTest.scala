package web

import org.http4s.Status._
import org.http4s.{Request, Response, Uri}
import org.scalatest.FunSpec
import org.scalatest.Matchers._
import overpoker.web.{Controller, HelloService}

class ControllerTest extends FunSpec {

  import Helpers._

  val controller = Controller.hello(TestHelloService)

  it("says your name right back at you"){
    val name = "chris"
    val request = Request(uri=Uri(path = s"/hello/$name"))
    val resp: Response = controller.run(request).run

    resp.status should be(Ok)
    resp.body.asString should be(TestHelloService.sayHello(name))

  }
}

object TestHelloService extends HelloService{
  override def sayHello(name: String): String = name + ", you silly bear"
}

