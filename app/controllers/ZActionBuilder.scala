package controllers

import zio._
import play.api.mvc._

trait ZActionBuilder[+R[_], B] {
  def apply[A](bodyParser: BodyParser[A]): ZActionBuilder[R, A]
  def apply(block: URIO[R[B], Result]): Action[B]
}

object ZActionBuilder {

  type Default = ZActionBuilder[Request, AnyContent]

  private def makeActionBuilder[R[_], B](
      Action: ActionBuilder[R, B]
  ): ZActionBuilder[R, B] = new ZActionBuilder[R, B] {

    def apply[A](bodyParser: BodyParser[A]) = makeActionBuilder(
      Action(bodyParser)
    )

    def apply(block: URIO[R[B], Result]): Action[B] = Action.async { req =>
      Runtime.default.unsafeRunToFuture(block.provide(req))
    }
  }

  val Live = ZLayer.fromService { Action: DefaultActionBuilder =>
    makeActionBuilder(Action)
  }
}
