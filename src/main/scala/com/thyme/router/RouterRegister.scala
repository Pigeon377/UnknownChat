package com.thyme.router

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.thyme.router.http.auth.{AuthLogin, AuthRegister}
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

            )
        }
    }

    private def authRouter(): Route = {
        pathPrefix("auth") {
            concat(
                AuthLogin.controller,
                AuthRegister.controller
                //                AuthUpdate.controller
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
