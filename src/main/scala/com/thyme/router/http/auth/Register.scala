package com.thyme.router.http.auth

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.thyme.actor.SingletonActor.userTransactionActor
import com.thyme.actor.database.{InsertUser, InsertUserSucceed, UserExist}
import com.thyme.extension.ExtensionFunction.generatePasswordHash
import com.thyme.model.User
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, jsonFormat3, mapFormat}
import spray.json.{RootJsonFormat, enrichAny}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext}
import scala.language.postfixOps

object Register {

    case class AuthRegisterResponse(status: Int, message: String, data: Map[String, String])

    implicit val tokenResponseFormat: RootJsonFormat[AuthRegisterResponse] = jsonFormat3(AuthRegisterResponse)
    implicit val timeout: Timeout = 7 seconds


    def controller(implicit ec: ExecutionContext): Route = {
        post {
            path("register") {
                formFieldMap { formParam =>
                    if (formParam.contains("name") && formParam.contains("mailbox") && formParam.contains("password")) {
                        Await.result(userTransactionActor ? InsertUser(
                            User(id=0L,mailbox = formParam("mailbox"), userName = formParam("name"), password = generatePasswordHash(formParam("password")))
                        ), 7 seconds) match {
                            case InsertUserSucceed(userId:Long) => complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                                AuthRegisterResponse(status = 1, message = "Succeed", data = Map(
                                    "user_id"->userId.toString
                                )).toJson.toString))
                            case UserExist() => complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                                AuthRegisterResponse(status = 0, message = "UserExist", data = Map()).toJson.toString))
                        }
                    } else {
                        complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                            AuthRegisterResponse(status = 0, "InvalidArgument", Map()).toJson.toString))
                    }
                }
            }
        }
    }
}
