import zio._

import models._

package object repos {
  def repoLive[Dep: Tag, A: Tag]( f: (Dep, Database) => A): URLayer[Has[Dep] & Has[Database], Has[A]] = {
    ZLayer.fromServices[Dep, Database, A](f)
  }
}
