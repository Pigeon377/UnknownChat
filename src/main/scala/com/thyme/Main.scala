package com.thyme

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import com.thyme.actor.room.RoomActor
import com.thyme.database.MongoExtensions
import com.thyme.extension.Tools.{rpcPort, system}
import com.thyme.router.RouterRegister
import com.thyme.router.auth.AuthLogin

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContextExecutor

object Main {

    def main(args:Array[String]): Unit ={
        implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
        implicit val executionContext: ExecutionContextExecutor = system.executionContext
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
