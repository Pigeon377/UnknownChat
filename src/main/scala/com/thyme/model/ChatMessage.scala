package com.thyme.model

import org.squeryl.KeyedEntity
import org.squeryl.dsl.ManyToOne


object ChatMessage {

    def apply(id: Long = 0L,
              sender: Long,
              body: String,
              roomId:Long): ChatMessage = {

        new ChatMessage(id, sender, body,roomId)

    }
}

class ChatMessage(val id: Long,
                  val sender: Long, // sender's uuid
                  val body: String,
                  val roomId: Long)
    extends KeyedEntity[Long]{
    lazy val room: ManyToOne[Room] = DataBase.roomToChatMessage.right(this)
}

