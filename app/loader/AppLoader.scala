package loader

import layers._
import play.api._
import play.api.mvc._
import play.api.routing.Router
import zio._

class AppLoader extends ApplicationLoader {

  val loader: AppContext.Loader = {
    (RouterLayer.routerLayer.map(_.get) <&> RouterLayer.filtersLayer.map(_.get))
  }

  def load(context: ApplicationLoader.Context): Application = {
    new AppContext(loader, context, Runtime.default).application
  }
}

class AppContext(
    loader: AppContext.Loader,
    context: ApplicationLoader.Context,
    runtime: Runtime[ZEnv]
) extends BuiltInComponentsFromContext(context) {
  val playRuntime: Runtime[PlayEnv] = runtime.map { zenv =>
    zenv ++ Has[BuiltInComponents](this) ++ Has(defaultActionBuilder)
  }
  lazy val (_router, _filters) = {
    val loadComp = for {
      compRes <- loader.build.reserve
      comp <- compRes.acquire
      _ <- Task.effect {
        applicationLifecycle.addStopHook(() =>
          playRuntime.unsafeRunToFuture(compRes.release(Exit.unit))
        )
      }
    } yield comp
    playRuntime.unsafeRun(loadComp)
  }

  def router = _router
  def httpFilters = _filters
}

object AppContext {
  type Loader = RLayer[PlayEnv, (Router, Seq[EssentialFilter])]
}
