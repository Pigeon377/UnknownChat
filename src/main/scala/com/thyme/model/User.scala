package com.thyme.model

class User(val uuid: Long,
           val userName: String,
           val mailbox: String,
           val password: String)

object User {
    def apply(uuid: Long, userName: String, mailbox: String, password: String): User = {
        new User(uuid, userName, mailbox, password)
    }
}