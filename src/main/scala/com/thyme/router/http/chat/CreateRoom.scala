package com.thyme.router.http.chat

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.thyme.actor.SingletonActor.roomTransactionActor
import com.thyme.actor.database.{InsertRoom, InsertRoomSucceed}
import com.thyme.extension.ExtensionFunction._
import com.thyme.model.Room
import spray.json.DefaultJsonProtocol.{IntJsonFormat, LongJsonFormat, StringJsonFormat, jsonFormat3}
import spray.json.{RootJsonFormat, enrichAny}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object CreateRoom {

    case class ChatCreateRoomResponse(status: Int, message: String, roomId: Long)

    implicit val tokenResponseFormat: RootJsonFormat[ChatCreateRoomResponse] = jsonFormat3(ChatCreateRoomResponse)
    implicit val timeout: Timeout = 7 seconds

    def controller(): Route = {
        post {
            path("create") {
                (formFieldMap & headerValueByName("token")) {
                    (formParam, tokenString) =>
                        val userId = checkJwtToken(tokenString)
                        val roomName = formParam("room_name")
                        if (userId == -1) {
                            complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                                ChatCreateRoomResponse(
                                    status = 0,
                                    message = "NeedLogin",
                                    roomId = -1L
                                ).toJson.toString
                            ))
                        } else {
                            Await.result(roomTransactionActor ? InsertRoom(Room(0, roomName)), 7 seconds) match {
                                case InsertRoomSucceed(id) => complete(StatusCodes.Accepted,
                                    HttpEntity(ContentTypes.`application/json`,
                                        ChatCreateRoomResponse(1, "Succeed", id).toJson.toString()
                                    ))
                            }
                        }

                }
            }
        }
    }

}
