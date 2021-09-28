package layers

import controllers._
import zio._

trait ApiLayers extends ServiceLayers {

  type ApiEnv = ZActionEnv & ZEnv

  val apiLayers = {
    (ZLayer.requires[ApiEnv] >+> serviceLayers) >>> {
        UserApi.Live ++ OrderApi.Live
    }
  }

}
