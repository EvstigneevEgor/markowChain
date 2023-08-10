package model

import io.circe
import io.circe.{Decoder, HCursor, Json}
import io.circe.generic.semiauto.deriveDecoder

object Message {
  case class TelegramExport(messages: Set[Message])

  case class Message(from: String, text: String)

  implicit def eitherDecoder[A, B](implicit a: Decoder[A], b: Decoder[B]): Decoder[Either[A, B]] = {
    val left: Decoder[Either[A, B]] = a.map(Left.apply)
    val right: Decoder[Either[A, B]] = b.map(Right.apply)
    left or right
  }

  implicit val messageDecoder: Decoder[Message] = (c: HCursor) =>
    for {
      from <- c.downField("from").as[Option[String]]
      text <- c.downField("text").as[Json]
    } yield Message(from.getOrElse("unexpected"), text.toString.drop(1).dropRight(1))
  implicit val telegramDecoder: Decoder[TelegramExport] = (c: HCursor) =>
    for {
      from <- c.downField("messages").as[Set[Message]]
    } yield TelegramExport(from.filterNot(_.text.contains("\"text\" :")))

  def decodeTelegram(input: String): Either[circe.Error, TelegramExport] = io.circe.parser.decode[TelegramExport](input)
}
