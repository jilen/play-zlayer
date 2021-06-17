package layers

import models._
import zio._


trait DatabaseLayer {
  val DatabaseLayer: RLayer[console.Console, Has[Database]] = ZLayer.fromManaged(Database.make)
}
