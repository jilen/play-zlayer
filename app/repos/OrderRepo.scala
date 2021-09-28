package repos

import models._
import zio._
import zio.console._

trait OrderRepo {
  def getOrders(userId: Long, page: Int, size: Int): Task[Seq[Order]]
}

object OrderRepo {
  final val Live = repoLive { (console: Console.Service, db: Database) =>

    new OrderRepo {
      def getOrders(userId: Long, page: Int, size: Int): Task[Seq[Order]] = {
        db.getConn.use { conn =>
          console.putStrLn(s"get orders of ${userId}").as(Seq.empty)
        }
      }
    }
  }
}
