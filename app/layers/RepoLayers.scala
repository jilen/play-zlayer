package layers

import models._
import repos._
import zio._

trait RepoLayers extends DatabaseLayer {
  val repoLayers =
    (ZLayer.identity[ZEnv] ++ DatabaseLayer) >>> {
      UserRepo.Live ++ OrderRepo.Live
    }
}
