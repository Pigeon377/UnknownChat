package com.thyme

import com.thyme.extension.Tools.rpcPort
import com.thyme.grpc.server.GrpcServer

object Main {

    def main(args:Array[String]): Unit ={
        val server =  new GrpcServer(rpcPort)
        server.start()
        // TODO
        // a user only need one websocket connect
        // we can push message to the user with
        // { "room_id":room_id,"message" : message }
        // let front-end finish message distribute
    }
}
