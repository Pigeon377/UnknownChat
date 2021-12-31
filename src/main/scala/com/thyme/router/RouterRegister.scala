package com.thyme.router

import akka.http.scaladsl.server.Directives.{concat,pathPrefix}
import akka.http.scaladsl.server.{Directives, Route}
import com.thyme.router.auth.{AuthLogin, AuthRegister}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.tools.nsc.interactive.Pickler.TildeDecorator

object RouterRegister {
    def registerRouter(implicit ex: ExecutionContext): Route = {
        pathPrefix("api") {
            authRouter()
        }
    }

    private def authRouter(): Route = {
        pathPrefix("auth") {
           concat(
                AuthLogin.authLogin,
                AuthRegister.authRegister
            )
        }
    }
}
