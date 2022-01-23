package com.thyme.model

import org.squeryl.KeyedEntity


object ChatMessage {

    def apply(id:Long = 0L,
              sender: Long,
              body: String,
              time: Long=System.currentTimeMillis()): ChatMessage = {

        new ChatMessage(id, sender,body,time)

     }
}

class ChatMessage (val id: Long,
                  val sender: Long, // sender's uuid
                  val body: String,
                  val time: Long)
    extends KeyedEntity[Long]

