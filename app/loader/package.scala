import akka.actor._
import play.api._
import play.api.mvc._

import _root_.controllers._
import zio._

package object loader {

  val ActionsLayer = ZLayer.fromService { comp: BuiltInComponents =>
    comp.defaultActionBuilder
  } >>> ZActionBuilder.Live

  type ZActionEnv = Has[ZActionBuilder.Default]

  type PlayEnv = ZEnv with Has[BuiltInComponents]
}
