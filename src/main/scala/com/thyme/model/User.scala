package com.thyme.model

import org.mongodb.scala.bson.ObjectId


object User {
    def apply(userName: String, mailbox: String, password: String): User = {
        new User(new ObjectId, userName = userName, mailbox, password, List(), List())
    }

}

class User(val _id: ObjectId = new ObjectId,
           val userName: String,
           val mailbox: String,
           val password: String,
           val friends: List[ObjectId] = List(),  // Friend's _id
           val rooms: List[ObjectId] = List())    // room's _id
