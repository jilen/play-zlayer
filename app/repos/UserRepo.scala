package repos

import models._
import zio._
import zio.console._

trait UserRepo {
  def getPass(name: String): UIO[Option[String]]
}

object UserRepo {
  val Live: URLayer[Console with Has[Database], Has[UserRepo]] = {
    repoLive { (cs: Console.Service, db: Database) =>
       new UserRepo {
        def getPass(name: String) = {
          db.getConn.use { conn =>
            cs.putStrLn(s"get pass with conn: ${conn}")
              .as(Some("fake_pass"))
              .orDie
          }
        }
      }
    }
  }
}
