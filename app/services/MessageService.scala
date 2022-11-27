package services

import models.Message
import repositories.MessageRepository

import javax.inject.Inject
import scala.concurrent.Future

class MessageService @Inject() (messageRepository: MessageRepository) {

  def getAll: Future[Seq[Message]] = messageRepository.getAll

  def getById(id: Long): Future[Option[Message]] = messageRepository.getById(id)

  def create(message: Message): Future[Option[Message]] = messageRepository.insert(message)

  def delete(id: Long): Future[Option[Int]] = messageRepository.delete(id)

  def update(id: Long, message: Message): Future[Option[Message]] = messageRepository.update(id, message)

}
