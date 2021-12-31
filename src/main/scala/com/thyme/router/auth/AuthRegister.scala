package com.thyme.router.auth

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, jsonFormat3, mapFormat}
import spray.json.{RootJsonFormat, enrichAny}

import scala.concurrent.ExecutionContext

object AuthRegister {

    case class AuthRegisterResponse(status:Int, message:String, data:Map[String,String])


    implicit val tokenResponseFormat: RootJsonFormat[AuthRegisterResponse] = jsonFormat3(AuthRegisterResponse)


    def authRegister(implicit ec: ExecutionContext): Route = {
        post {
            path("register") {
                formFieldMap { formParam =>
                    if (formParam.contains("name") && formParam.contains("mailbox") && formParam.contains("password")) {
                        //TODO
                        // put user message in database
                        complete(StatusCodes.Accepted, HttpEntity(
                            ContentTypes.`application/json`,
                            AuthRegisterResponse(
                                status = 1,
                                "Succeed",
                                Map()
                            ).toJson.toString
                        ))
                    }else{
                        complete(StatusCodes.Accepted, HttpEntity(
                            ContentTypes.`application/json`,
                            AuthRegisterResponse(
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
