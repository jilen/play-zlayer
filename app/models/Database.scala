package models

import zio._

trait Database {
  def getConn: UManaged[Database.Conn]
}

object Database {

  trait Conn

  def makeDB: RIO[console.Console, Database] = RIO.access[console.Console] { env =>
    new Database {
      val _console = env.get[console.Console.Service]
      val makeConn = _console.putStrLn(s"Make conn").as(new Conn {}).orDie

      def getConn = Managed.make(makeConn)(c => _console.putStrLn(s"Release conn: ${c}").orDie)
    }
  }

  def make: RManaged[console.Console, Database] = {
    ZManaged.make(makeDB)(db =>
      zio.console.putStrLn(s"$db closed").orDie
    )
  }
}
