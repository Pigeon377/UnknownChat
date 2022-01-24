package com.thyme.model

import org.squeryl.dsl.{ManyToMany, OneToMany}
import org.squeryl.{KeyedEntity, Query, Table}


object Room {
    def apply(id: Long, roomName: String): Room = {

        new Room(id, roomName)

    }
}

class Room(val id: Long, val name: String) extends KeyedEntity[Long] {

    lazy val users: Query[User] with ManyToMany[User, LinkUserAndRoom]
    = DataBase.linkUserAndRoom.right(this)

    lazy val chatMessage: OneToMany[ChatMessage]
    = DataBase.roomToChatMessage.left(this)

}
