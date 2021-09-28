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

  def makeUserApi(
      userService: UserService,
      Action: ZActionBuilder.Default
  ): UserApi = {
    new UserApi {
      def login = Action {
        UIO(Results.Ok("success"))
      }
    }
  }

  val Live = {
    ZLayer.fromServices(makeUserApi)
  }
}
