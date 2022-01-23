package com.thyme.model


object ChatMessage {

    def apply(uuid:Long,
              sender: Long,
              body: String,
              time: Long=System.currentTimeMillis()): ChatMessage = {

        new ChatMessage(uuid, sender,body,time)

     }
}

class ChatMessage(val uuid: Long,
                  val sender: Long, // sender's uuid
                  val body: String,
                  val time: Long)
