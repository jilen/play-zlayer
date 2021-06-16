package loader

import controllers._
import play.api.routing.Router
import zio._

trait ApiRoutesLayers extends ServiceLayers {

  private def userRoutes(userApi: Has[UserApi]): Router.Routes = {
    case h if h.path == "/user/login" => userApi.get.login
  }

  private final val userApiRoutesLayer: RLayer[PlayEnv, Has[Router.Routes]] = {
    (userServiceLayer ++ ZLayer.identity[PlayEnv]) >>> UserApi.Live >>> ZLayer.fromFunction(userRoutes)
  }

  final val apiRoutesLayer = userApiRoutesLayer.map( out => Seq(out.get[Router.Routes]))

}
