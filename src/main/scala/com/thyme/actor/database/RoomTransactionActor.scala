package com.thyme.actor.database

import akka.actor.Actor
import com.thyme.model.{DataBase, Room}
import org.squeryl.PrimitiveTypeMode._

class RoomTransactionActor extends Actor {
    override def receive: Receive = {

        case InsertRoom(room) => insertNewRoom(room)
        case QueryRoom(roomId) => queryRoom(roomId)
        case ChangeRoomName(roomId, newName) => changeRoomName(roomId, newName)

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


}
