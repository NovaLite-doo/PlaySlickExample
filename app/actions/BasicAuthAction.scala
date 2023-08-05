package actions

import play.api.mvc.Results.Unauthorized
import play.api.mvc._
import services.AuthService

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

class BasicAuthAction @Inject()(
  parser: BodyParsers.Default,
  authService: AuthService
)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] =
    request.headers.get("Authorization") match {
      case Some(auth) =>
        authService
          .basicAuth(auth)
          .flatMap {
            case true => block(request)
            case false => Future.successful(Unauthorized)
          }
      case _ =>
        Future.successful(Unauthorized)
    }
}
