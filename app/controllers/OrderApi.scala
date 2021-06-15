package controllers

import models._
import zio._

trait OrderApi {
  def getOrders(userId: Long): Task[Seq[Order]]
}
