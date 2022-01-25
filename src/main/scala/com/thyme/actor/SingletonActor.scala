package com.thyme.actor

import akka.actor.{ActorRef, Props}
import com.thyme.actor.database.TransactionActor
import com.thyme.extension.FinalParam.system

object SingletonActor {
    val databaseTransactionActor: ActorRef = system.actorOf(Props[TransactionActor], "MongoTransactionActor")
}
