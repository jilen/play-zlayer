package loader

import services._
import zio._


trait ServiceLayers extends RepoLayers {
  val userServiceLayer: RLayer[console.Console, Has[UserService]] = userRepoLayer >>> UserService.Live
}
