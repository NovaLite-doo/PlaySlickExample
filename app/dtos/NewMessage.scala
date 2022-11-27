package dtos

import models.Message
import play.api.libs.json.{Json, Reads}

import scala.language.implicitConversions

case class NewMessage(content: String)

object NewMessage {
  implicit val jsonReader: Reads[NewMessage] = Json.reads[NewMessage]

  implicit def toModel(newMessage: NewMessage): Message = Message(0, newMessage.content)
}
