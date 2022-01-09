package com.thyme.extension

import akka.actor.{ActorPath, ActorSystem}
import scala.collection.mutable

object FinalParam {

    val rpcPort = 50051

    val system: ActorSystem = ActorSystem("Thyme")

    val roomIDMapToActorPath = new mutable.HashMap[Long,ActorPath]()

}
