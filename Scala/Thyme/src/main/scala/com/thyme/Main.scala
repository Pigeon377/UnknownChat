package com.thyme

import com.thyme.extension.Tools.rpcPort
import com.thyme.grpc.server.GrpcServer

object Main {

    def main(args:Array[String]): Unit ={
        val server =  new GrpcServer(rpcPort)
        server.start()
    }
}
