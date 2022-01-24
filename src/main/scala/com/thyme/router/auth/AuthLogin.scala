package com.thyme.router.auth

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.thyme.extension.ExtensionFunction
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, jsonFormat3, mapFormat}
import spray.json.{RootJsonFormat, enrichAny}
import com.thyme.actor.SingletonActor.mongoTransactionActor
import com.thyme.actor.database.{QuerySucceed, QueryUser, UserUnExist}
import com.thyme.extension.ExtensionFunction._
import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object AuthLogin {

    case class AuthLoginResponse(status: Int, message: String, data: Map[String, String])

    implicit val tokenResponseFormat: RootJsonFormat[AuthLoginResponse] = jsonFormat3(AuthLoginResponse)
    implicit val timeOut: Timeout = 10 seconds

    def controller(implicit ec: ExecutionContext): Route = {
        post {
            path("login") {
                formFieldMap { formParam =>
                    if (formParam.contains("user_id") && formParam.contains("password")) {
                        val userId:Option[Int] = formParam("user_id").toIntOption
                        val password: String = formParam("password")
                        if (userId.isEmpty){ // user_id contains some char which is not digit
                            complete(StatusCodes.Accepted, HttpEntity(
                                ContentTypes.`application/json`,
                                AuthLoginResponse(
                                    status = 0,
                                    "InvalidArgument",
                                    Map()
                                ).toJson.toString
                            ))
                        }

                        mongoTransactionActor ? QueryUser(userId) match {
                            case QuerySucceed(user) =>
                                if (checkPasswordHash(password, user.password)) {
                                    complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                                        AuthLoginResponse(
                                            status = 1,
                                            message = "Succeed",
                                            data = Map("token" -> ExtensionFunction.generateJwtToken(userId))
                                        ).toJson.toString
                                    ))
                                } else {
                                    complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                                        AuthLoginResponse(
                                            status = 0,
                                            message = "MailboxUnMatchPassword",
                                            data = Map()
                                        ).toJson.toString
                                    ))
                                }
                            case UserUnExist() => complete(StatusCodes.Accepted, HttpEntity(
                                ContentTypes.`application/json`,
                                AuthLoginResponse(
                                    status = 0,
                                    message = "MailboxUnMatchPassword",
                                    data = Map()
                                ).toJson.toString
                            ))
                        }
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
