package loader

import controllers._
import play.api._
import play.api.routing.Router
import play.api.mvc._
import zio._

object RouterLayer extends ApiRoutesLayers {

  private val routesLayer: RLayer[PlayEnv, Seq[Router.Routes]] = apiRoutesLayer

  val routerLayer: RLayer[PlayEnv, Router] = {
    routesLayer.map { routes =>
      routes.foldLeft(Router.empty) { (router, routes) =>
        router.orElse(Router.from(routes))
      }
    }
  }

  val filtersLayer: RLayer[PlayEnv, Seq[EssentialFilter]] = {
    ZLayer.identity[PlayEnv].map(_ => Seq.empty)
  }
}
