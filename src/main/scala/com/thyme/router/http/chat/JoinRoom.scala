package com.thyme.router.http.chat

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import com.thyme.extension.ExtensionFunction.checkJwtToken
import com.thyme.router.http.chat.CreateRoom.ChatCreateRoomResponse
import spray.json.enrichAny

import scala.concurrent.Await


/**
 * with database operation
 * different from 'WebsocketConnect' JoinRoom
 * */
object JoinRoom {
    def controller(): Unit ={
        post{
            path("join"){
                (formFieldMap & headerValueByName("token")){
                    (formParam,tokenString) =>
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
                        }else{
                            Await()
                        }
                }
            }
        }
    }
}
