package layers

import controllers._
import zio._

trait ApiLayers extends ServiceLayers {

  private def apiLayerOf[SR >: ZEnv, S, AR >: S with ZActionEnv, A](
      serviceLayer: RLayer[SR, S],
      apiLive: RLayer[AR, A]
  )(implicit
      ev: Has.Union[S, ZActionEnv],
      tag: Tag[ZActionEnv]
  ): RLayer[ZEnv with ZActionEnv, A] = {
    (serviceLayer ++ ZLayer.identity[ZActionEnv]) >>> apiLive
  }

  type ApiEnv = ZActionEnv with ZEnv

  val userApiLayer: RLayer[ApiEnv, Has[UserApi]] = {
    apiLayerOf(userServiceLayer, UserApi.Live)
  }

}
