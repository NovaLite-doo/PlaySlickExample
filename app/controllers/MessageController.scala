package controllers

import actions.BasicAuthAction
import dtos.NewMessage
import models.Message
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.MessageService

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MessageController @Inject() (val controllerComponents: ControllerComponents, messageService: MessageService, basicAuthAction: BasicAuthAction)(implicit ec: ExecutionContext)
    extends BaseController {

  def getAll: Action[AnyContent] = Action.async {
    messageService.getAll.map(messages => Ok(Json.toJson(messages)))
  }

  def getById(id: Long): Action[AnyContent] = Action.async {
    messageService.getById(id).map {
      case Some(message) => Ok(Json.toJson(message))
      case None          => NotFound(s"Message with id: $id doesn't exist")
    }
  }

  def create: Action[NewMessage] = basicAuthAction.async(parse.json[NewMessage]) { request =>
    val messageToAdd: Message = request.body

    messageService.create(messageToAdd).map {
      case Some(message) => Created(Json.toJson(message))
      case None          => Conflict
    }
  }

  def delete(id: Long): Action[AnyContent] = basicAuthAction.async {
    messageService.delete(id).map {
      case Some(_) => NoContent
      case None    => NotFound
    }
  }

  def update(id: Long): Action[Message] = basicAuthAction.async(parse.json[Message]) { request =>
    if (id != request.body.id)
      Future.successful(BadRequest("Id in path must be equal to id in body"))
    else
      messageService.update(id, request.body).map {
        case Some(message) => Ok(Json.toJson(message))
        case None          => NotFound
      }
  }

}
