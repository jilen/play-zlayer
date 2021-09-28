name := "play-zio"

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "1.0.12"
)

scalacOptions ++= Seq("-Xsource:3")

enablePlugins(PlayScala)
