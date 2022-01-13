package com.thyme.router

import akka.http.scaladsl.server.Directives.{concat, pathPrefix}
import akka.http.scaladsl.server.Route
import com.thyme.router.auth.{AuthLogin, AuthRegister, AuthUpdate}
import com.thyme.router.chat.ChatSession

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

object RouterRegister {
    def registerRouter(implicit ex: ExecutionContext): Route = {
        pathPrefix("api") {
            authRouter()
            chatRouter()
        }
    }

    private def chatRouter():Route={
        pathPrefix("chat"){
            concat(
                ChatSession.controller
            )
        }
    }

    private def authRouter(): Route = {
        pathPrefix("auth") {
            concat(
                AuthLogin.controller,
                AuthRegister.controller,
                AuthUpdate.controller
            )
        }
    }
}
