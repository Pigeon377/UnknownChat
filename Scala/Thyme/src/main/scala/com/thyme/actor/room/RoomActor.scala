package com.thyme.actor.room

import akka.actor.{Actor, ActorRef, Props}
import com.thyme.extension.Tools.{roomIDMapToActorPath, system}

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class RoomActor(val roomID: Long,
                var roomName: String) extends Actor {

    val userList = new java.util.LinkedList[Long]()


    override def receive: Receive = {
        case AddNewUser(uuid) => this.addNewUser(uuid)
        case RemoveUser(uuid) => this.removeUser(uuid)
        case IsUserExist(uuid) => this.isInUserList(uuid)
        case ChangeRoomName(newName) => this.changeRoomName(newName)
        case BroadcastMessage(message) => broadcastMessage(message)
    }


    def broadcastMessage(message: String): Boolean = {
        //TODO
        // broadcast message in this room
        true
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

    def apply(roomID: Long): ActorRef = {
        val actor = system.actorOf(Props[RoomActor], roomID.toString)
        roomIDMapToActorPath.put(roomID,actor.path)
        actor
    }

    def isRoomExist(roomID:Long): Boolean ={
        roomIDMapToActorPath.contains(roomID)
    }

    def getActorWithRoomID(roomID:Long): ActorRef ={
        // TODO use roomID to get a RoomActor
        // if this actor exist ,return this actor;
        // if it isn't exist, think about two possible
        // 1. room exist, but it be saved in database and not in memory
        // 2. room unExist,you should return null to the caller
        null
    }

}