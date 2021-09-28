package services

import models._
import repos._
import zio._

trait OrderService {
  def getOrders(userId: Long, page: Int, size: Int): Task[Seq[Order]]
}

object OrderService {

  val Live = ZLayer.fromService { orderRepo: OrderRepo =>

    new OrderService {

      def getOrders(userId: Long, page: Int, size: Int): Task[Seq[Order]] = {
        orderRepo
          .getOrders(userId, page, size)
      }
    }
  }
}
