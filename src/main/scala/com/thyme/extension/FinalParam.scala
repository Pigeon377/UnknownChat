package com.thyme.extension

import akka.actor.ActorSystem

object FinalParam {

    val rpcPort = 50051

    val system: ActorSystem = ActorSystem("Thyme")

}
