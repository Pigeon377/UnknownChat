package com.thyme.actor

import akka.actor.{ActorRef, Props}
import com.thyme.actor.database.TransactionActor
import com.thyme.extension.FinalParam.system

object SingletonActor {
    val mongoTransactionActor: ActorRef = system.actorOf(Props[TransactionActor], "MongoTransactionActor")
}
