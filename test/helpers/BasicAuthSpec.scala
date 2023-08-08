package helpers

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BasicAuthSpec extends AnyWordSpec with Matchers {

  "valid auth" in {
    val auth = "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ=="
    BasicAuth.parse(auth) mustBe Some(("Aladdin", "open sesame"))
  }

  "invalid auth" in {
    val values = Seq(
      "QWxhZGRpbjpvcGVuIHNlc2FtZQ==",
      "QWxhZGRpbjpvcGVuIHNlc2FtZQ",
      "QWxhZcGVuIHNlc2FtZQ==",
      "Basic Alladin:open sesame",
      "Basic Alladin:",
      "Basic :open sesame",
      "Alladin:open sesame",
      "Basic",
    )
    values.map(BasicAuth.parse).forall(_.isEmpty)
  }
}
