package com.thyme

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import com.thyme.router.RouterRegister
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.MySQLAdapter

import scala.concurrent.{ExecutionContextExecutor, Future}

object Main {

    def main(args: Array[String]): Unit = {

        registerDataBase()
        registerRouter()
        // TODO
        // a user only need one websocket connect
        // we can push message to the user with
        // { "room_id":room_id,"message" : message }
        // let front-end finish message distribute
    }

    def registerRouter(): Future[Http.ServerBinding] = {
        implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "AkkaHttp")
        implicit val executionContext: ExecutionContextExecutor = system.executionContext
        Http().newServerAt("localhost", 2333).bind(RouterRegister.registerRouter)
    }

    def registerDataBase(): Unit = {
        val databaseUsername = "root"
        val databasePassword = "3777777"
        val databaseConnection = "jdbc:mysql://localhost:3306/thyme"

        Class.forName("com.mysql.cj.jdbc.Driver")
        SessionFactory.concreteFactory = Some(() => Session.create(
            java.sql.DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword),
            new MySQLAdapter
        ))


    }
}
