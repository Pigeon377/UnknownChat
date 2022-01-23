package com.thyme.model

import org.squeryl.KeyedEntity

object Room {
    def apply(id: Long, roomName: String, createTime: Long = System.currentTimeMillis()): Room = {
        new Room(id, roomName, createTime)
    }
}

class Room(val id: Long, val name: String, val createTime: Long) extends KeyedEntity[Long]
