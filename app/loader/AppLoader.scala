package loader

import play.api._
import play.api.mvc._
import play.api.routing.Router

import zio._

object AppLoader extends ApplicationLoader {

  val loader: AppContext.Loader = ???

  def load(context: ApplicationLoader.Context): Application = {
    new AppContext(loader, context, Runtime.default).application
  }
}

class AppContext(
    loader: AppContext.Loader,
    context: ApplicationLoader.Context,
    runtime: Runtime[ZEnv]
) extends BuiltInComponentsFromContext(context) {
  lazy val (_router, _filters) = {
    val loadComp = for {
      compRes <- loader.build.provide(this).reserve
      comp <- compRes.acquire
      _ <- Task.effect {
        applicationLifecycle.addStopHook(() =>
          runtime.unsafeRunToFuture(compRes.release(Exit.unit))
        )
      }
    } yield comp
    runtime.unsafeRun(loadComp)
  }

  def router = _router
  def httpFilters = _filters
}

object AppContext {
  type Loader = RLayer[BuiltInComponents, (Router, Seq[EssentialFilter])]
}
