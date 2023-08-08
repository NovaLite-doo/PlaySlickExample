package services

import com.github.t3hnar.bcrypt._
import helpers.BasicAuth
import repositories.UserRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AuthService @Inject() (userRepository: UserRepository)(implicit ec: ExecutionContext) {

  private val salt = "$2a$10$tdBXWNyNDnp0HbnjWtK3g."

  def basicAuth(auth: String): Future[Boolean] =
    BasicAuth.parse(auth) match {
      case Some((username, password)) => authenticate(username, password)
      case None => Future.successful(false)
    }

  private def authenticate(username: String, password: String): Future[Boolean] =
    userRepository
      .getByUsername(username)
      .map {
        case Some(user) => user.password == password.bcryptBounded(salt)
        case _ => false
      }
}
