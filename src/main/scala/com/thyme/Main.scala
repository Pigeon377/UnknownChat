package com.thyme

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.stream.Materializer
import com.thyme.router.RouterRegister
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.MySQLAdapter

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

object Main {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "AkkaHttp")
    implicit val materializer: Materializer = Materializer.matFromSystem
    implicit val executionContext: ExecutionContextExecutor =
        ExecutionContext.fromExecutor(Executors.newFixedThreadPool(16))

    def main(args: Array[String]): Unit = {

        registerDataBase()
        registerRouter()

    }

    def registerRouter(): Future[Http.ServerBinding] = {
        Http().newServerAt("localhost", 2333)
            .bind(RouterRegister.registerRouter(com.thyme.extension.FinalParam.system,materializer))
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
