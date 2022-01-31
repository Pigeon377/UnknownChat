package com.thyme.actor.database

import akka.actor.Actor
import com.thyme.model.{DataBase, Room}
import org.squeryl.PrimitiveTypeMode._

class RoomTransactionActor extends Actor {
    override def receive: Receive = {

        case InsertRoom(room) => insertNewRoom(room)
        case QueryRoom(roomId) => queryRoom(roomId)
        case ChangeRoomName(roomId, newName) => changeRoomName(roomId, newName)
        case JoinUserToRoom(roomId, userId) => joinUserToRoom(roomId, userId)
    }

    private def insertNewRoom(room: Room): Unit = {
        transaction {
            DataBase.rooms.insert(room)
            sender() ! InsertRoomSucceed(DataBase.rooms.allRows.size)
        }
    }

    private def queryRoom(roomId: Long): Unit = {
        transaction {
            val room = DataBase.rooms.where(x => roomId === x.id)
            if (room.isEmpty) {
                sender() ! RoomUnExist()
            } else {
                sender() ! QueryRoomSucceed(room.head, room.head.users.toList)
            }
        }
    }

    private def changeRoomName(roomId: Long, newName: String): Unit = {
        transaction {
            update(DataBase.rooms)(r => where(r.id === roomId) set (r.name := newName))
        }
    }

    private def joinUserToRoom(roomId: Long, userId: Long): Unit = {
        transaction {
            val user = DataBase.users.where(u => u.id === userId).head
            val room = DataBase.rooms.where(r => r.id === roomId).head
            user.rooms.associate(room)
            room.users.associate(user)
            DataBase.users.update(user)
            DataBase.rooms.update(room)
        }
    }


}
