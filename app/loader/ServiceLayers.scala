package loader

import services._
import zio._


trait ServiceLayers extends RepoLayers {
  val userServiceLayer: RLayer[PlayEnv, Has[UserService]] = userRepoLayer >>> UserService.Live
}
