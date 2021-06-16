package services

import repos._
import zio._

trait UserService {}

object UserService {
  val Live: URLayer[Has[UserRepo], Has[UserService]] = ZLayer.fromFunction { repo: Has[UserRepo] =>
    new UserService {}
  }
}
