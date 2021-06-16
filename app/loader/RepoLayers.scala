package loader

import models._
import repos._
import zio._

trait RepoLayers extends DatabaseLayer {
  val userRepoLayer: RLayer[console.Console, Has[UserRepo]] =
    DatabaseLayer >>> UserRepo.Live
}
