package models

case class User(id: Long, name: String, pass: String)

object User {

  sealed trait LoginErr extends Err {
    val baseCode = 100
    val subCode: Int
    val msg: String
    final def code = baseCode * 100 + subCode
  }

  object LoginErr {

    object Codes {
      final val InvalidUserOrPass = 1
    }

    case object InvalidUserOrPass extends LoginErr {
      val subCode = Codes.InvalidUserOrPass
      val msg = "用户名或密码无效"
    }
  }
}
