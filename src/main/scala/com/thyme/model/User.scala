package com.thyme.model

import org.squeryl.KeyedEntity

class User(val id: Long = 0L,
           val userName: String,
           val mailbox: String,
           val password: String)
    extends KeyedEntity[Long]

object User {
    def apply(id: Long, userName: String, mailbox: String, password: String): User = {
        new User(id, userName, mailbox, password)
    }
}