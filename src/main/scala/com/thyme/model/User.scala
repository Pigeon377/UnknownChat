package com.thyme.model

import org.mongodb.scala.bson.ObjectId


object User {
    def apply(userName: String, mailbox: String, password: String): User = {
        new User(new ObjectId, userName = userName, mailbox, password, List(), List())
    }

    def apply(_id:ObjectId,userName: String, mailbox: String, password: String,friends: List[ObjectId],rooms:List[ObjectId]): User = {
        new User(new ObjectId, userName = userName, mailbox, password, friends,rooms)
    }

}

class User(val _id: ObjectId = new ObjectId,
           val userName: String,
           val mailbox: String,
           val password: String,
           val friends: List[ObjectId] = List(),  // Friend's _id
           val rooms: List[ObjectId] = List())    // room's _id
