import play.api._
import play.api.mvc._

import zio._

package object loader {
  type PlayEnv = ZEnv with Has[BuiltInComponents] with Has[DefaultActionBuilder]
}
