package com.thyme.router.auth

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.thyme.extension.ExtensionFunction
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, jsonFormat3, mapFormat}
import spray.json.{RootJsonFormat, enrichAny}

import scala.concurrent.ExecutionContext

object AuthLogin {

    case class AuthLoginResponse(status: Int, message: String, data: Map[String,String])

    implicit val tokenResponseFormat: RootJsonFormat[AuthLoginResponse] = jsonFormat3(AuthLoginResponse)

    def authLogin(implicit ec: ExecutionContext): Route = {
        post {
            path("login") {
                formFieldMap { formParam =>
                    if (formParam.contains("mailbox") && formParam.contains("password")) {
                        //TODO
                        // check user password and mailbox from database
                        complete(StatusCodes.Accepted, HttpEntity(
                            ContentTypes.`application/json`,
                            AuthLoginResponse(
                                status = 1,
                                "Succeed",
                                Map("token" -> ExtensionFunction.generateJwtToken(114514))
                            ).toJson.toString
                        ))
                    } else {
                        complete(StatusCodes.Accepted, HttpEntity(
                            ContentTypes.`application/json`,
                            AuthLoginResponse(
                                status = 0,
                                "InvalidArgument",
                                Map()
                            ).toJson.toString
                        ))
                    }
                }
            }
        }
    }
}
