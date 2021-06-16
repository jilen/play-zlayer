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
      (userService: UserService, Action: DefaultActionBuilder) =>
        new UserApi {
          def login = Action {
            Results.Ok("success")
          }
        }
    }
  }
}
