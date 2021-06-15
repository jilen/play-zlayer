package models

case class User(id: Long, name: String, pass: String)

object User {
  sealed trait LoginErr extends Err {
    val baseCode = 100
  }

  object LoginErr {
    def apply(subCode: Int, _msg: String): LoginErr = {
      new LoginErr {
        def code = baseCode * 100 + subCode
        def msg = _msg
      }
    }
  }
}
