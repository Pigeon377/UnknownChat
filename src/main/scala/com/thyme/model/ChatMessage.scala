package com.thyme.model

import org.mongodb.scala.bson.ObjectId

object ChatMessage {
    def apply(sender: ObjectId, body: String): ChatMessage = {
        new ChatMessage(time = System.currentTimeMillis(), sender = sender, body = body)
    }
}

class ChatMessage(val time: Long,
                  val sender: ObjectId,
                  val body: String)
