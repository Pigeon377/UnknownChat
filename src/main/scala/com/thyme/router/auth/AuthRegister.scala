package com.thyme.router.auth

import akka.actor.Props
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.thyme.actor.database.{InsertSucceed, InsertUser, MongoTransactionActor, UserExist}
import com.thyme.extension.Tools.system
import com.thyme.model.User
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, jsonFormat3, mapFormat}
import spray.json.{RootJsonFormat, enrichAny}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object AuthRegister {

    case class AuthRegisterResponse(status: Int, message: String, data: Map[String, String])

    implicit val tokenResponseFormat: RootJsonFormat[AuthRegisterResponse] = jsonFormat3(AuthRegisterResponse)
    implicit val timeout: Timeout = 7 seconds

    private val mongoTransactionActor = system.actorOf(Props[MongoTransactionActor], "MongoTransactionActor")

    def controller(implicit ec: ExecutionContext): Route = {
        post {
            path("register") {
                formFieldMap { formParam =>
                    if (formParam.contains("name") && formParam.contains("mailbox") && formParam.contains("password")) {
                        //TODO
                        // put user message in database
                        var status: Boolean = false
                        // maybe we can find a new method to use val instead of this var?
                        (mongoTransactionActor ? InsertUser(
                            userName = formParam("name"), mailbox = formParam("mailbox"), password = formParam("password")
                        )).onComplete { mes =>
                            status = mes.get match {
                                case InsertSucceed() => true
                                case UserExist() => false
                            }
                        }
                        println(status)
                        complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                            string = if (status) {
                                AuthRegisterResponse(status = 1, message = "Succeed", data = Map()).toJson.toString
                            } else {
                                AuthRegisterResponse(status = 0, message = "UserExist", data = Map()).toJson.toString
                            }))
                    } else {
                        complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                            AuthRegisterResponse(status = 0, "InvalidArgument", Map()).toJson.toString))
                    }
                }
            }
        }
    }
}
