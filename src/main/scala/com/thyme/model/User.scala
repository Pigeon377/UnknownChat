package com.thyme.model

import org.mongodb.scala.bson.ObjectId


object User {
    def apply(userName: String, mailbox: String, password: String): User = {
        User(_id = new ObjectId,
            userName = userName,
            mailbox: String,
            password = password,
            friends = List[Long](),
            rooms = List[Long]())
    }

    def apply(_id: ObjectId, userName: String, mailbox: String, password: String, friends: List[Long], rooms: List[Long]): User = {
        User(_id = new ObjectId,
            userName = userName,
            mailbox: String,
            password = password,
            friends = friends,
            rooms = rooms)
    }
}

case class User(_id: ObjectId, userName: String, mailbox: String, password: String, friends: List[Long], rooms: List[Long])
