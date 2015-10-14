package web

import org.http4s._

object Helpers {
  // https://github.com/gvolpe/simple-http4s-api/blob/master/src/test/scala/com/gvolpe/api/service/HttpServiceSpec.scala
  implicit class ByteVector2String(body: EntityBody) {
    def asString: String = {
      val array = body.runLog.run.reduce(_ ++ _).toArray
      new String(array.map(_.toChar))
    }
  }
}
