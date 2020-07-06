package carbyne.akkaHttp

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import net.liftweb.json.DefaultFormats

import scala.concurrent.ExecutionContextExecutor

object Server extends Directives with JsonSupport {
  def main(args: Array[String]): Unit = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher
    val songWordOperations = new SongWordOperations

    val route =
      concat(
        post {
          path("Beatles") {
            entity(as[SongWord]) { songWord =>
              complete(songWord match {
                case SongWord("Yes", 1) => SongWord("No", 1)
                case SongWord("Stop", 2) => SongWord("GoGoGo", 2)
                case SongWord("GoodBye", 3) => SongWord("Hello", 3)
                case _ => SongWord("Invalid Song Word", 0)
              })
            }
          }
        },
        post {
          path("UnBeatles") {
            entity(as[SongWord]) { songWord =>
              complete(songWord match {
                case SongWord("No", 1) => SongWord("Yes", 1)
                case SongWord("GoGoGo", 2) => SongWord("Stop", 2)
                case SongWord("Hello", 3) => SongWord("GoodBye", 3)
                case _ => SongWord("Invalid Song Word", 0)
              })
            }
          }
        },
        post {
          path("Beatles" / IntNumber) { ID =>
            completeWith(instanceOf[SongWord]) { completionFunction =>
              songWordOperations.processSongWords(ID, completionFunction)
            }
          }
        }
      )
    Http().bindAndHandle(route, "localhost", 9999)
  }
}
