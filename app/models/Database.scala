package models

import zio._

trait Database {}

object Database {
  def make: RManaged[console.Console, Database] = {
    ZManaged.make(Task.succeed(new Database {}))(db =>
      zio.console.putStrLn(s"$db closed").orDie
    )
  }
}
