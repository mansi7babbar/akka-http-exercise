package carbyne.akkaHttp

class SongWordOperations {
  def processSongWords: (Int, SongWord => Unit) => Unit = (ID: Int, complete: SongWord => Unit) => {
    complete(ID match {
      case 1 => SongWord("YesAndNo", 1)
      case 2 => SongWord("StopAndGoGoGo", 2)
      case 3 => SongWord("GoodByeAndHello", 3)
      case _ => SongWord("Invalid Song Word", 0)
    })
  }
}
