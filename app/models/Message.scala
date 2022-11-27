package models

import play.api.libs.json.{Json, OFormat}

case class Message(id: Long, content: String)

object Message {
  implicit val jsonFormat: OFormat[Message] = Json.format[Message]
}
