package com.thyme.actor.room

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import com.thyme.Test.timeout
import com.thyme.extension.FinalParam.{roomIDMapToActorPath, system}
import com.thyme.model.{DataBase, User}
import org.squeryl.PrimitiveTypeMode._
import com.thyme.actor.SingletonActor.roomTransactionActor
import com.thyme.actor.database.{QueryRoom, QueryRoomSucceed, RoomUnExist}

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class RoomActor(val roomID: Long,
                var userList:List[User])
    extends Actor {

    // every user will be abstracted to a actor (but i don't know it's type)
    // userId to ActorRef
    val userConnectionMap: mutable.Map[Long, ActorRef] = mutable.Map.empty[Long, ActorRef]

    override def receive: Receive = {
        case JoinRoom(userId,actorRef) => this.joinRoom(userId,actorRef)
        case RemoveUser(userId) => this.removeUser(userId)
        case ChangeRoomName(newName) => this.changeRoomName(newName)
        case BroadcastMessage(message) => this.broadcastMessage(message)
        case _ => println("[Warning!]   Unknown Message in RoomActor receive method")
    }

    def broadcastMessage(message: String): Unit = {
        userConnectionMap.values.foreach(_ ! message)
    }


    /**
     * @param actorRef is websocket connect ActorRef
     *                 put id and it in the map
     *                 user can receive message in this room
     * */
    private def joinRoom(userId: Long,actorRef: ActorRef):Unit = {
//    future    this.userList.contains(userId)
        this.userConnectionMap.put(userId,actorRef)
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
        this.roomName = newRoomName
    }


}


object RoomActor {

    private val roomActorMap = mutable.Map[Long, ActorRef]()

    /**
     * @param roomID using roomID to get right room actor
     * @return
     *      ActorRef => get room which you find
     *      null     => room is UnExist
     * */
    def apply(roomID: Long): ActorRef = {
        val roomActorOption = roomActorMap.get(roomID)
        if (roomActorOption.isEmpty) {
            Await.result(roomTransactionActor ? QueryRoom(roomID), 7 seconds) match {
                case QueryRoomSucceed(room, users) =>
                    val roomActor = system.actorOf(Props[RoomActor](room.id,users))
                    this.roomActorMap.put(roomID, roomActor)
                    roomActor
                case RoomUnExist() =>
                    null
            }
        } else {
            roomActorOption.get
        }
    }
}