package overpoker.web

import org.http4s.server._
import org.http4s.dsl._

trait HelloService{
  def sayHello(name: String): String
}

object RealHelloService extends HelloService{
  override def sayHello(name: String): String = s"Hello, $name"
}

object Controller {
  def hello(service: HelloService) = HttpService {
    case GET -> Root / "hello" / name =>
      Ok(service.sayHello(name))
  }
}