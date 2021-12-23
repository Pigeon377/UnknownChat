package com.thyme

import com.thyme.service.GrpcServer
import io.grpc.ServerBuilder

object Application {
    val port = 50051
    def main(args:Array[String]): Unit ={
        val server =  new GrpcServer(50051)
        server.start()
    }
}
