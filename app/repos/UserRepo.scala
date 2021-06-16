package repos

import models._
import zio._

trait UserRepo {
  def getPass(name: String): UIO[Option[String]]
}

object UserRepo {
  val Live = {
    ZLayer.fromServices { (db: Database, cs: console.Console.Service) =>
      val repo: UserRepo = new UserRepo {
        def getPass(name: String) = {
          db.getConn.use { conn =>
            cs.putStrLn(s"get pass with conn: ${conn}")
              .as(Some("fake_pass"))
              .orDie
          }
        }
      }
      repo
    }
  }
}
