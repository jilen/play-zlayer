package controllers

import models._
import play.api.mvc._
import services._
import zio._

trait OrderApi {
  def getOrders(userId: Long, page: Int): Action[AnyContent]
}

object OrderApi {

  def makeOrderApi(orderService: OrderService, Action: ZActionBuilder.Default): OrderApi = new OrderApi {
    def getOrders(userId: Long, page: Int) = Action {
      for {
        req <- URIO.identity[Request[AnyContent]]
        orders <- orderService.getOrders(userId, page, 10).!
      } yield Results.Ok("")
    }
  }

  final val Live = ZLayer.fromServices(makeOrderApi)
}
