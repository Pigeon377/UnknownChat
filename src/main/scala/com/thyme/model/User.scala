package com.thyme.model

import org.squeryl.dsl.ManyToMany
import org.squeryl.{KeyedEntity, Query}

class User(val id: Long = 0L,
           val userName: String,
           val mailbox: String,
           val password: String)
    extends KeyedEntity[Long]{
    lazy val rooms: Query[Room] with ManyToMany[Room, LinkUserAndRoom]
    = DataBase.linkUserAndRoom.left(this)
}

object User {
    def apply(id: Long, userName: String, mailbox: String, password: String): User = {
        new User(id, userName, mailbox, password)
    }
}