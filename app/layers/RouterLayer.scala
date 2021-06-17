package layers

import controllers._
import play.api._
import play.api.routing.Router
import play.api.mvc._
import zio._

object RouterLayer extends ApiRoutesLayers {

  val routerLayer: RLayer[PlayEnv, Has[Router]] = {
    (ZLayer.identity[PlayEnv] >+> ActionsLayer) >>> apiRoutesLayer >>> ZLayer
      .fromService { routes: Router.Routes =>
        Router.from(routes)
      }
  }

  val filtersLayer: RLayer[PlayEnv, Has[Seq[EssentialFilter]]] = {
    ZLayer.identity[PlayEnv].map(_ => Has(Seq.empty))
  }
}
