package com.thyme

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import com.thyme.router.RouterRegister

import scala.concurrent.ExecutionContextExecutor

object Main {

    def main(args: Array[String]): Unit = {
        implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
        implicit val executionContext: ExecutionContextExecutor = system.executionContext
        Test.start()
        val bindingFuture = Http()
            .newServerAt("localhost", 2333)
            .bind(RouterRegister.registerRouter)
        // TODO
        // a user only need one websocket connect
        // we can push message to the user with
        // { "room_id":room_id,"message" : message }
        // let front-end finish message distribute
    }
}
