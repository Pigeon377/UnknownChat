package com.thyme.model

import org.squeryl.KeyedEntity


object ChatMessage {

    def apply(id: Long = 0L,
              sender: Long,
              body: String,
              time: Long = System.currentTimeMillis(),
              roomId:Long): ChatMessage = {

        new ChatMessage(id, sender, body, time,roomId)

    }
}

class ChatMessage(val id: Long,
                  val sender: Long, // sender's uuid
                  val body: String,
                  val time: Long,
                  val roomId: Long)
    extends KeyedEntity[Long]{
    lazy val room = DataBase.roomToChatMessage.right(this)
}

