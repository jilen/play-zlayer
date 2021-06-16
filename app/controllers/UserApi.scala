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
  val Live: URLayer[PlayEnv with Has[UserService], Has[UserApi]] =
    ZLayer.fromFunction { env =>

      val Action = env.get[DefaultActionBuilder]

      new UserApi {
        def login = Action {
          Results.Ok("success")
        }
      }
    }
}
