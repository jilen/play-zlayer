package layers

import controllers._
import play.api.mvc._
import play.api.routing.Router
import zio._

trait ApiRoutesLayers extends ApiLayers {

  type ApiLayer = RLayer[ApiEnv, Has[Router.Routes]]

  private def apiRoutes[A: Tag](
      apiLayer: RLayer[ApiEnv, Has[A]]
  )(routesDef: A => Router.Routes): ApiLayer = {
    apiLayer >>> ZLayer.fromService(routesDef)
  }

  private final val userApiRoutesLayer: ApiLayer = {
    apiRoutes(userApiLayer) { userApi =>
      {
        case h if h.path == "/user/login" => userApi.login
      }
    }
  }

  val zActionEnvLayer: RLayer[ZActionEnv, ZActionEnv] =
    ZLayer.identity[ZActionEnv]

  type ZApiEnv = ZEnv with ZActionEnv

  final val apiRoutesLayer = router(Seq(userApiRoutesLayer))

  private def router[R: Tag, E: Tag](
      routess: Seq[ZLayer[R, E, Has[Router.Routes]]]
  ) = {

    val emptyLayer: ZLayer[R, E, Has[Router.Routes]] =
      ZLayer.succeed(PartialFunction.empty)
    routess.foldLeft(emptyLayer) { (agg, curr) =>
      agg.zipWithPar(curr) { (hasA, hasB) =>
        Has(hasA.get orElse hasB.get)
      }
    }
  }

}
