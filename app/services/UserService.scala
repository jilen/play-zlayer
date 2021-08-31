package services

import models._
import repos._
import zio._

trait UserService {
  def login(name: String, pass: String): IO[User.LoginErr, Unit]
}

object UserService {
  val Live = ZLayer.fromService {
    userRepo: UserRepo =>
      new UserService {
        def login(name: String, pass: String): IO[User.LoginErr, Unit] = {
          userRepo
            .getPass(name)
            .filterOrFail(_.exists(p => p == pass))(
              User.LoginErr.InvalidUserOrPass
            )
            .as(())
        }
      }
  }
}
