package com.thyme.router.http.chat

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.thyme.Test.timeout
import com.thyme.extension.ExtensionFunction.checkJwtToken
import com.thyme.router.http.chat.CreateRoom.ChatCreateRoomResponse
import spray.json.{RootJsonFormat, enrichAny}
import com.thyme.actor.SingletonActor.roomTransactionActor
import com.thyme.actor.database.JoinUserToRoom
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, jsonFormat3, mapFormat}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps


/**
 * with database operation
 * different from 'WebsocketConnect' JoinRoom
 * */
object JoinRoom {

    case class JoinRoomResponse(status: Int, message: String, data: Map[String, String])

    implicit val tokenResponseFormat: RootJsonFormat[JoinRoomResponse] = jsonFormat3(JoinRoomResponse)
    implicit val timeout: Timeout = 7 seconds

    def controller(): Route = {
        post {
            path("join") {
                (formFieldMap & headerValueByName("token")) {
                    (formParam, tokenString) =>
                        val userId = checkJwtToken(tokenString)
                        val roomId = formParam("room_id").toLongOption
                        if (userId == -1) {
                            complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                                JoinRoomResponse(
                                    status = 0,
                                    message = "NeedLogin",
                                    data = null
                                ).toJson.toString
                            ))
                        } else {
                            Await.result(roomTransactionActor ? JoinUserToRoom(roomId = roomId.get, userId = userId), 7 seconds)
                            complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                                JoinRoomResponse(
                                    status = 1,
                                    message = "Succeed",
                                    data = null
                                ).toJson.toString
                            ))
                        }
                }
            }
        }
    }
}
