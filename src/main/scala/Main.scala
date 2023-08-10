
object Main {
  def main(args: Array[String]): Unit = {
    val pars = new parser.Parser("result.json")

    pars.parse() match {
      case Left(e) =>
        e.printStackTrace()
        println(s"FAILED: $e")

      case Right(telegramExport) =>
        val messages = telegramExport.messages
        println(messages.size)
        messages.foreach(println)
      //todo тут написать код для того чтобы работать с telegramExport
    }
  }
}