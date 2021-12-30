package com.thyme

import com.thyme.actor.room.RoomActor
import com.thyme.extension.Tools.{rpcPort, system}

object Main {

    def main(args:Array[String]): Unit ={
        // TODO
        // a user only need one websocket connect
        // we can push message to the user with
        // { "room_id":room_id,"message" : message }
        // let front-end finish message distribute
    }
}
