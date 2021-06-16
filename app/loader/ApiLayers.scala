package loader

import controllers._
import play.api.routing.Router
import zio._

trait ApiRoutesLayers extends ServiceLayers {

  private def userRoutes(userApi: UserApi): Router.Routes = {
    case h if h.path == "/user/login" => userApi.login
  }

  private final val userApiRoutesLayer: RLayer[console.Console with Has[ZActionBuilder.Default], Has[Router.Routes]] = {
    (userServiceLayer ++ ZLayer.service[ZActionBuilder.Default]) >>> UserApi.Live >>> ZLayer.fromService(userRoutes)
  }

  final val apiRoutesLayer: RLayer[ZEnv with ZActionEnv, Has[Seq[Router.Routes]]] = userApiRoutesLayer >>> ZLayer.fromService { routes: Router.Routes =>
    Seq(routes)
  }

}
