package web

import cats.effect.IO
import fs2.text
import org.http4s._

object Helpers {
  // https://github.com/gvolpe/simple-http4s-api/blob/master/src/test/scala/com/gvolpe/api/service/HttpServiceSpec.scala
  implicit class ByteVector2String(body: EntityBody[IO]) {
    def asString: String = {
      body.through(text.utf8Decode).compile.string.unsafeRunSync()
    }
  }
}
