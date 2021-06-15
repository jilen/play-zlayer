package controllers

import models._
import zio._

trait UserApi {
  def login(name: String, pass: String): IO[Err, Unit]
}
