package com.thyme.actor

import akka.actor.{ActorRef, Props}
import com.thyme.actor.database.MongoTransactionActor
import com.thyme.extension.Tools.system

object SingletonActor {
    val mongoTransactionActor: ActorRef = system.actorOf(Props[MongoTransactionActor], "MongoTransactionActor")

}
