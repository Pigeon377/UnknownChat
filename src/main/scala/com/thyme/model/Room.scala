package com.thyme.model

object Room {
    def apply(uuid:Long,roomName:String,createTime:Long=System.currentTimeMillis()): Room = {
        new Room(uuid,roomName,createTime)
    }
}

class Room(val uuid:Long,val roomName:String,val createTime:Long) {

}
