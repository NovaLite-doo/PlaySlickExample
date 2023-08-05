package helpers

import java.util.Base64
import scala.util.Try
import scala.util.matching.Regex

object BasicAuth {

  private val basicAuthPattern: Regex = "Basic (.*)".r
  private val credentialsPattern: Regex = "(.*):(.*)".r

  def parse(auth: String): Option[(String, String)] =
    for {
      base64Creds <- parseBasicAuth(auth)
      creds <- base64Decode(base64Creds)
      (username, password) <- parseCredentials(creds)
    } yield (username, password)

  private def parseBasicAuth(auth: String): Option[String] =
    auth match {
      case basicAuthPattern(base64Creds) => Some(base64Creds)
      case _ => None
    }

  private def base64Decode(str: String): Option[String] =
    Try(new String(Base64.getDecoder.decode(str))).toOption

  private def parseCredentials(creds: String): Option[(String, String)] =
    creds match {
      case credentialsPattern(username, password) => Some((username, password))
      case _ => None
    }
}
