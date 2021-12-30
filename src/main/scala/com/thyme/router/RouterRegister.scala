package com.thyme.router

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.Route
import com.thyme.router.auth.AuthLogin

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

object RouterRegister {
    def registerRouter(implicit ex: ExecutionContext): Route = {
        pathPrefix("api"){
            routerAuth()
        }
    }

    private def routerAuth(): Route = {
        pathPrefix ("auth"){
            AuthLogin.authRouter
        }
    }
}
