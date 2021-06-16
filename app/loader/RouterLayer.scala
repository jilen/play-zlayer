package loader

import controllers._
import play.api._
import play.api.routing.Router
import play.api.mvc._
import zio._

object RouterLayer extends ApiRoutesLayers {

  private val routesLayer: RLayer[ZEnv with ZActionEnv, Has[Seq[Router.Routes]]] = apiRoutesLayer

  val routerLayer: RLayer[PlayEnv, Has[Router]] = {
    (ZLayer.identity[PlayEnv] >+> ActionsLayer) >>> routesLayer >>> ZLayer.fromService { routes: Seq[Router.Routes] =>
      routes.foldLeft(Router.empty) { (router, routes) =>
        router.orElse(Router.from(routes))
      }
    }
  }

  val filtersLayer: RLayer[PlayEnv, Has[Seq[EssentialFilter]]] = {
    ZLayer.identity[PlayEnv].map(_ => Has(Seq.empty))
  }
}
