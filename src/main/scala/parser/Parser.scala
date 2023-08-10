package parser

import io.circe.jawn.decode
import model.Message
import model.Message.TelegramExport

import scala.io.Source

/**
 * Класс который будет обробатывать выгрузку из телеграмма и генерировать файлы такого формата
 * Из строк
 * а б в г
 * б а г в
 * =====
 * а: б, г
 * б: в, a
 * в: г,
 * г: в
 * note: придется ввести спец символ для конца сообщения и для начала
 */
import java.nio.file.{Files, Paths}

class Parser(path: String) {
  //todo  прочитать что такое итератор
  def readFile(): Iterator[String] = {
    Source.fromFile(path).getLines
  }

  /**
   * метод для тестов
   */
  def print(iterator: Iterator[String]): Unit = {
    iterator.foreach(println)
  }

  def parse() = {
    val json = Files.readString(Paths.get(path))
    decode[TelegramExport](json)
  }

}
