package com.thyme.router

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.thyme.router.http.auth.{Login, Register}
import com.thyme.router.http.chat.{CreateRoom, JoinRoom}
import com.thyme.router.websocket.WebSocketConnect

import scala.concurrent.ExecutionContext.Implicits.global

object RouterRegister {
    def registerRouter(implicit system: ActorSystem, mat: Materializer): Route = {
        pathPrefix("api") {
            concat(
                authRouter(),
                chatRouter(),
                websocketRouter(system,mat),
            )
        }
    }


    private def chatRouter(): Route = {
        pathPrefix("chat") {
            concat(
                CreateRoom.controller(),
                JoinRoom.controller()
            )
        }
    }

    private def authRouter(): Route = {
        pathPrefix("auth") {
            concat(
                Login.controller,
                Register.controller
                //                Update.controller
            )
        }
    }
    def websocketRouter(implicit system: ActorSystem, mat: Materializer): Route = {
        pathPrefix("ws") {
            concat(
                WebSocketConnect.controller
            )
        }
    }


}
