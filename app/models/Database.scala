package models

import zio._

trait Database {
  def getConn: UManaged[Database.Conn]
}

object Database {

  trait Conn

  private def makeDB = RIO.service[console.Console.Service].map { _console =>
    new Database {
      val makeConn = _console.putStrLn(s"Make conn").as(new Conn {}).orDie
      def getConn = Managed.make(makeConn)(c =>
        _console.putStrLn(s"Release conn: ${c}").orDie
      )
    }
  }

  def make: RManaged[console.Console, Database] = {
    ZManaged.make(makeDB)(db => zio.console.putStrLn(s"$db closed").orDie)
  }
}
