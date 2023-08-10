
object Main {
  def main(args: Array[String]): Unit = {
    val pars = new parser.Parser("result.json")

    pars.parse() match {
      case Left(e) =>
        e.printStackTrace()
        println(s"FAILED: $e")

      case Right(telegramExport) =>
        println(telegramExport.messages.filterNot(_.text.contains("\"text\" :")).size)
        telegramExport.messages.filterNot(_.text.contains("\"text\" :")).foreach(println)
      //todo тут написать код для того чтобы работать с telegramExport
    }
  }
}