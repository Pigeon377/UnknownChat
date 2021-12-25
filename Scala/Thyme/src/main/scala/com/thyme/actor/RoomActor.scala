package com.thyme.actor

import akka.actor.{Actor, ActorRef, Props}
import com.thyme.extension.Tools

class RoomActor(val roomID: Long,
                var roomName: String) extends Actor {

    val userList = new java.util.LinkedList[Long]()

    /**
     * @return
     * true => this user is in userList
     * false=> this user not in userList
     * */
    def isInUserList(uuid: Long): Boolean = {
        userList.contains(uuid)
    }

    /**
     * @return
     * true => add operator succeed!
     * false=> user exist! failed!
     * */
    def addNewUser(uuid: Long): Boolean = {
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
    def removeUser(uuid: Long): Boolean = {
        if (isInUserList(uuid)) {
            userList.remove(uuid)
            true
        } else {
            false
        }
    }


    override def receive: Receive = { message =>
        println(message)
        // TODO
        // broadcast message in this room
    }
}


object RoomActor {

    def createNewRoom(roomID: Long): ActorRef = {
        Tools.system.actorOf(Props[RoomActor], roomID.toString)
    }

}