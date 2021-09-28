package layers

import controllers._
import play.api._
import play.api.mvc._
import zio._

object RouterLayer extends ApiLayers {

  val routerLayer: RLayer[PlayEnv, Has[router.Routes]] = {
    (ZLayer.requires[PlayEnv] >+> ActionsLayer >+> apiLayers).map { env => // might gets automatic construction by macros
      val routes = new router.Routes(
        env.get[BuiltInComponents].httpErrorHandler,
        env.get[UserApi],
        env.get[OrderApi]
      )
      Has(routes)
    }
  }

  val filtersLayer: RLayer[PlayEnv, Has[Seq[EssentialFilter]]] = {
    ZLayer.identity[PlayEnv].map(_ => Has(Seq.empty))
  }
}
