package com.thyme.actor.room

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import com.thyme.Test.timeout
import com.thyme.actor.SingletonActor.{roomTransactionActor, userTransactionActor}
import com.thyme.actor.database.{ChangeRoomName, QueryRoom, QueryRoomSucceed, QueryUserJoinedAllRoom, QueryUserJoinedAllRoomSucceed, RoomUnExist}
import com.thyme.actor.room.RoomActor.convert
import com.thyme.extension.FinalParam.system
import com.thyme.model.User
import com.thyme.router.websocket.WebSocketConnectResponseMessage
import spray.json.DefaultJsonProtocol.{LongJsonFormat, StringJsonFormat, jsonFormat3}
import spray.json.{RootJsonFormat, enrichAny}

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class RoomActor(val roomID: Long,
                var roomName:String = "Unknown Room",
                var userList:List[User]=List())
    extends Actor {

    lazy val userIdList: List[Long] = userList.map(x => x.id)

    // every user will be abstracted to a actor (but i don't know it's type)
    // userId to ActorRef
    val userConnectionMap: mutable.Map[Long, ActorRef] = mutable.Map.empty[Long, ActorRef]

    override def receive: Receive = {
        case JoinRoom(userId,actorRef) => this.joinRoom(userId,actorRef)
        case LeftRoom(userId) => this.leftRoom(userId)
        case ChangeName(newName) => this.changeRoomName(newName)
        case BroadcastMessage(message) => this.broadcastMessage(message)
        case _ => println("[Warning!]   Unknown Message in RoomActor receive method")
    }

    def broadcastMessage(message: String): Unit = {
        val res:String = WebSocketConnectResponseMessage(this.roomID,this.roomName,message).toJson.toString()
        userConnectionMap.values.foreach(_ ! res)
    }


    /**
     * @param actorRef is websocket connect ActorRef
     *                 put id and it in the map
     *                 user can receive message in this room
     * */
    private def joinRoom(userId: Long,actorRef: ActorRef):Unit = {
        if (userIdList.contains(userId)) {
            this.userConnectionMap.put(userId, actorRef)
        }
        // Future: else return can't joined message
    }


    private def leftRoom(userId:Long): Unit ={
        this.userConnectionMap.remove(userId)
    }

//    future! /**
//     * @return
//     * true => delete operator succeed!
//     * false=> user UnExist! failed!
//     * */
//    private def removeUser(userId: Long): Boolean = {
//        if (isInUserList(userId)) {
//            userList.remove(userId)
//            true
//        } else {
//            false
//        }
//    }

    private def changeRoomName(newRoomName: String): Unit = {
        Await.result(roomTransactionActor ? ChangeRoomName(this.roomID,newRoomName),7 seconds)
        this.roomName = newRoomName
        // Future will check database update succeed or not and depend on its result to change room name
    }


}


object RoomActor {

    private val roomActorMap = mutable.Map[Long, ActorRef]()

    /**
     * @param roomID using roomID to get right room actor
     * @return
     *      ActorRef => get room actor which you find
     *      null     => room is UnExist
     * */
    def apply(roomID: Long): ActorRef = {
        val roomActorOption = roomActorMap.get(roomID)
        if (roomActorOption.isEmpty) {
            Await.result(roomTransactionActor ? QueryRoom(roomID), 7 seconds) match {
                case QueryRoomSucceed(room, users) =>
                    val roomActor = system.actorOf(Props(new RoomActor(room.id,room.name,users.toList)))
                    this.roomActorMap.put(roomID, roomActor)
                    roomActor
                case RoomUnExist() =>
                    null
            }
        } else {
            roomActorOption.get
        }
    }

    def joinAllRoomWithUserId(userId:Long,actorRef: ActorRef): Unit ={
        Await.result(userTransactionActor ? QueryUserJoinedAllRoom(userId),7 seconds) match {
            case QueryUserJoinedAllRoomSucceed(userList) =>
                userList.foreach(x=>RoomActor(x) ! JoinRoom(userId,actorRef))
        }
    }



    implicit def convert: RootJsonFormat[WebSocketConnectResponseMessage] = jsonFormat3(WebSocketConnectResponseMessage)
}