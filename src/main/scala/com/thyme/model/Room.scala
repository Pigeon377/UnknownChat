package com.thyme.model

import org.mongodb.scala.bson.ObjectId

object Room {
    def apply(members: List[ObjectId]): Room = {
        new Room(new ObjectId,
            members = members,
            List()
        )
    }
}

class Room(val _id: ObjectId,
           val members: List[ObjectId],
           val messages: List[ChatMessage]) {

}
