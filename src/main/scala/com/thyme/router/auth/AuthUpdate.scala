package com.thyme.router.auth

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.thyme.actor.SingletonActor.mongoTransactionActor
import com.thyme.actor.database.{UpdateSucceed, UpdateUser, UserUnExist}
import com.thyme.extension.ExtensionFunction.checkJwtToken
import com.thyme.model.User
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, jsonFormat3, mapFormat}
import spray.json.{RootJsonFormat, enrichAny}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext}
import scala.language.postfixOps

object AuthUpdate {

    case class AuthLoginResponse(status: Int, message: String, data: Map[String, String])

    implicit val tokenResponseFormat: RootJsonFormat[AuthLoginResponse] = jsonFormat3(AuthLoginResponse)
    implicit val timeOut: Timeout = 10 seconds

    def controller(implicit ec: ExecutionContext): Route = {
        post {
            path("update") {
                (formFieldMap & headerValueByName("token") & parameter("op")) { (formParam, token, op) =>
                    if (!token.isBlank) { // token must be take
                        if (formParam.contains("mailbox") && !op.isBlank) {
                            if (checkJwtToken(token, formParam("mailbox"))) {
                                val mailbox: String = formParam("mailbox")
                                Await.result(mongoTransactionActor ? UpdateUser(
                                    user = op match {
                                        case "1" => User(userName = formParam("new_username"), mailbox = mailbox, password = "")
                                        case "0" => User(userName = "", mailbox = mailbox, password = formParam("new_password"))
                                    },
                                    operatorCode = op match {
                                        case "0" => 0
                                        case "1" => 1
                                    }), 7 seconds) match {
                                    case UpdateSucceed() =>
                                        complete(StatusCodes.Accepted, HttpEntity(
                                            ContentTypes.`application/json`,
                                            AuthLoginResponse(
                                                status = 1,
                                                message = "Succeed",
                                                data = Map()
                                            ).toJson.toString))
                                    case UserUnExist() => complete(StatusCodes.Accepted, HttpEntity(
                                        ContentTypes.`application/json`,
                                        AuthLoginResponse(
                                            status = 0,
                                            "MailboxUnMatchPassword",
                                            Map()
                                        ).toJson.toString
                                    ))
                                }
                            } else {
                                complete(StatusCodes.Accepted, HttpEntity(
                                    ContentTypes.`application/json`,
                                    AuthLoginResponse(
                                        status = 0,
                                        message = "NoLoginUser",
                                        data = Map()
                                    ).toJson.toString))
                            }

                        } else {
                            complete(StatusCodes.Accepted, HttpEntity(
                                ContentTypes.`application/json`,
                                AuthLoginResponse(
                                    status = 0,
                                    message = "InvalidArgument",
                                    data = Map()
                                ).toJson.toString))
                        }
                    } else {
                        complete(StatusCodes.Accepted, HttpEntity(
                            ContentTypes.`application/json`,
                            AuthLoginResponse(
                                status = 0,
                                message = "UnLoginUser",
                                data = Map()
                            ).toJson.toString
                        ))
                    }
                }
            }
        }
    }
}
