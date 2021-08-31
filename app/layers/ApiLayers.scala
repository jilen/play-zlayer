package layers

import controllers._
import zio._

trait ApiLayers extends ServiceLayers {

  private def apiLayerOf[SR >: ZEnv, S, AR >: S & ZActionEnv, A](
      serviceLayer: RLayer[SR, S],
      apiLive: RLayer[AR, A]
  )(implicit
      ev: Has.Union[S, ZActionEnv],
      tag: Tag[ZActionEnv]
  ): RLayer[ZEnv & ZActionEnv, A] = {
    (serviceLayer ++ ZLayer.identity[ZActionEnv]) >>> apiLive
  }

  type ApiEnv = ZActionEnv & ZEnv

  val userApiLayer: RLayer[ApiEnv, Has[UserApi]] = {
    apiLayerOf(userServiceLayer, UserApi.Live)
  }

}
