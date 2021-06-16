package repos

import models._
import zio._

trait UserRepo {

}

object UserRepo {
  val Live: URLayer[Has[Database], Has[UserRepo]] = ZLayer.fromFunction { db =>
    new UserRepo {}
  }
}
