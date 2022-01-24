package com.thyme.model

import org.squeryl.dsl.{ManyToMany, OneToMany}
import org.squeryl.{KeyedEntity, Query, Table}


object Room {
    def apply(id: Long, roomName: String, createTime: Long = System.currentTimeMillis()): Room = {

        new Room(id, roomName, createTime)

    }
}

class Room(val id: Long, val name: String, val createTime: Long) extends KeyedEntity[Long] {

    lazy val users: Query[User] with ManyToMany[User, LinkUserAndRoom]
    = DataBase.linkUserAndRoom.right(this)

    lazy val chatMessage: OneToMany[ChatMessage]
    = DataBase.roomToChatMessage.left(this)

}
