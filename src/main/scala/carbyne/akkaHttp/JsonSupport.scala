package carbyne.akkaHttp

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val songWordFormat: RootJsonFormat[SongWord] = jsonFormat2(SongWord)
}
