package layers

import repos._
import models._
import services._
import zio._

trait ServiceLayers extends RepoLayers {
  val serviceLayers =
    repoLayers >>> {
      UserService.Live ++ OrderService.Live
    }
}
