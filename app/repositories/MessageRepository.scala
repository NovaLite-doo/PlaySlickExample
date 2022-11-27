package repositories

import models.Message
import org.postgresql.util.PSQLException
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class MessageRepository @Inject() (override protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val messages = TableQuery[MessageTable]

  def getAll: Future[Seq[Message]] = db.run(messages.result)

  def getById(id: Long): Future[Option[Message]] = db.run(messages.filter(_.id === id).result).map(_.headOption)

  def insert(message: Message): Future[Option[Message]] =
    db.run((messages returning messages) += message)
      .map(Some.apply[Message])
      .recover { case e: PSQLException =>
        None
      }

  def delete(id: Long): Future[Option[Int]] = {
    db.run(messages.filter(_.id === id).delete).map {
      case 0       => None
      case 1       => Some(1)
      case deleted => throw new RuntimeException(s"Deleted $deleted rows")
    }
  }

  def update(id: Long, message: Message): Future[Option[Message]] = {
    db.run(messages.filter(_.id === id).update(message).map {
      case 0       => None
      case 1       => Some(message)
      case updated => throw new RuntimeException(s"Updated $updated rows")
    })
  }

  class MessageTable(tag: Tag) extends Table[Message](tag, "messages") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def content = column[String]("content")

    override def * = (id, content) <> ((Message.apply _).tupled, Message.unapply)
  }

}
