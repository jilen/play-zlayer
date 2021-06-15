package loader

import controllers._
import play.api._
import play.api.routing.Router
import play.api.mvc._
import zio._

package object layers {
  val apiLayer: RLayer[BuiltInComponents, Has[UserApi] with Has[OrderApi]] = ???
  val routerLayer: RLayer[BuiltInComponents, Router] = {
    apiLayer.map { apis =>
      Router.from({
        case h if h.path == "/user/get/" =>
      })
    }
  }
  val filtersLayer: RLayer[BuiltInComponents, Seq[EssentialFilter]] = {
    ???
  }
}
