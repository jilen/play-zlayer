package layers

import controllers._
import play.api.mvc._
import play.api.routing.Router
import play.api.routing.sird._
import zio._

trait ApiRoutesLayers extends ApiLayers {

  type ApiLayer = RLayer[ApiEnv, Has[Router.Routes]]

}
