package controllers

import models._
import services._
import zio._
import play.api.mvc._
import loader._

trait UserApi {
  def login: Action[AnyContent]
}

object UserApi {
  val Live = {
    ZLayer.fromServices {
      (userService: UserService, Action: ZActionBuilder.Default) =>
        new UserApi {
          def login = Action {
            UIO(Results.Ok("success"))
          }
        }
    }
  }
}
