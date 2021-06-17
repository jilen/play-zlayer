package layers

import repos._
import models._
import services._
import zio._

trait ServiceLayers extends RepoLayers {
  val userServiceLayer: RLayer[ZEnv, Has[UserService]] =
    userRepoLayer >>> UserService.Live
}
