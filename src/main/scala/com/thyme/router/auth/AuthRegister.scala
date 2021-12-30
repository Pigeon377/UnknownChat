package com.thyme.router.auth

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, jsonFormat3, mapFormat}
import spray.json.{RootJsonFormat, enrichAny}

import scala.concurrent.ExecutionContext

object AuthRegister {

    case class TokenResponse(status:Int,message:String,data:Map[String,Map[String,String]])


    implicit val tokenResponseFormat: RootJsonFormat[TokenResponse] = jsonFormat3(TokenResponse)


    def authRouter(implicit ec: ExecutionContext): Route = {
        post {
            path("register") {
                formFieldMap { formParam =>
                    if (formParam.contains("name") && formParam.contains("mailbox") && formParam.contains("password")) {
                        complete(StatusCodes.Accepted, HttpEntity(
                            ContentTypes.`application/json`,
                            TokenResponse(
                                status = 1,
                                "Succeed",
                                Map("token"->Map("114514"->"1919810","114514"->"11451491"))
                            ).toJson.toString
                        ))
                    }else{
                        complete(StatusCodes.Accepted, HttpEntity(
                            ContentTypes.`application/json`,
                            TokenResponse(
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
