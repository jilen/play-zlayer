# play-zlayer

### Intro

An play! framework managed by `ZLayer`


### How ?

Play compile `routes` file into a class like

``` scala
class Routes(
  httpErrorHandler: HttpErrorHandler,
  xxxControler0: XXXController0,
  xxxControler1: XXXController1,
  ...
)
```

 Assume there are layer
```
val controllerLayers = ZLayer[R, XXX, Has[BuiltInComponents] with  Has[XXXControler0] with Has[XXXControler1] ...]
```

it could be easily `map` to `Routes` above

```scala
controllerLayers.map { env =>
  val _routes = Routes(
    env.get[BuiltInComponents].httpErrorHandler,
    env.get[XXXControler0],
    env.get[XXXControler1],
    ...
  )
  Has(_routes)
}
```
