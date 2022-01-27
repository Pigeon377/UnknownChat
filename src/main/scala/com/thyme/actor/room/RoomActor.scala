package com.thyme.actor.room

import akka.actor.{Actor, ActorRef, Props}
import com.thyme.extension.FinalParam.{roomIDMapToActorPath, system}

import scala.collection.mutable
import scala.language.postfixOps

class RoomActor(val roomID: Long,
                var roomName: String,
                val creatorUUID: Long) extends Actor {

    // every user will be abstracted to a actor (but i don't know it's type)
    val users:mutable.Map[String,ActorRef] = mutable.Map.empty[String, ActorRef]

    override def receive: Receive = {
        case AddNewUser(uuid) => this.addNewUser(uuid)
        case RemoveUser(uuid) => this.removeUser(uuid)
        case IsUserExist(uuid) => this.isInUserList(uuid)
        case ChangeRoomName(newName) => this.changeRoomName(newName)
        case BroadcastMessage(message) => this.broadcastMessage(message)
        case _ => println("[Warning!]   Unknown Message in RoomActor receive method")
    }


    /**
     * when you want to check user is pass check or not
     * use ExtensionFunction.checkTokenNumber(uuid) equals check the checkNumber is right
     * message should like this
     * {
     * "uuid":xxx,
     * "message" : message_body,
     * "check_number":xxx
     * }
     * */
    def broadcastMessage(message: String):Unit = {
        users.values.foreach(_ ! message)
    }


    /**
     * @return
     * true => this user is in userList
     * false=> this user not in userList
     * */
    private def isInUserList(uuid: Long): Boolean = {
        userList.contains(uuid)
    }

    /**
     * @return
     * true => add operator succeed!
     * false=> user exist! failed!
     * */
    private def addNewUser(uuid: Long): Boolean = {
        if (isInUserList(uuid)) {
            false
        } else {
            userList.add(uuid)
            true
        }
    }

    /**
     * @return
     * true => delete operator succeed!
     * false=> user UnExist! failed!
     * */
    private def removeUser(uuid: Long): Boolean = {
        if (isInUserList(uuid)) {
            userList.remove(uuid)
            true
        } else {
            false
        }
    }

    private def changeRoomName(newRoomName: String): Unit = {
        this.roomName = newRoomName
    }

}


object RoomActor {

    def apply(roomID: Long, roomName: String, creatorUUID: Long): ActorRef = {
        val actor = system.actorOf(Props(new RoomActor(roomID = roomID, roomName = roomName, creatorUUID = creatorUUID)), roomID.toString)
        roomIDMapToActorPath.put(roomID, actor.path)
        actor
    }

    def isRoomExist(roomID: Long): Boolean = {
        roomIDMapToActorPath.contains(roomID)
    }

    def getActorWithRoomID(roomID: Long): ActorRef = {
        // TODO use roomID to get a RoomActor
        // if this actor exist ,return this actor;
        // if it isn't exist, think about two possible
        // 1. room exist, but it be saved in database and not in memory
        // 2. room unExist,you should return null to the caller
        null
    }

}