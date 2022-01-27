package com.thyme.actor.database

import akka.actor.Actor
import com.thyme.model.{DataBase, Room}
import org.squeryl.PrimitiveTypeMode._
class RoomTransactionActor extends Actor{
    override def receive: Receive = {

        case InsertRoom(room:Room) => insertNewRoom(room)
        case QueryRoom(roomId:Long)=>

    }

    private def insertNewRoom(room: Room): Unit ={
        transaction{
            DataBase.rooms.insert(room)
            sender() ! InsertRoomSucceed(DataBase.rooms.allRows.size)
        }
    }
}
