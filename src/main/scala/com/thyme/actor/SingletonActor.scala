package com.thyme.actor

import akka.actor.{ActorRef, Props}
import com.thyme.actor.database.{RoomTransactionActor, UserTransactionActor}
import com.thyme.extension.FinalParam.system

object SingletonActor {
    val userTransactionActor: ActorRef = system.actorOf(Props[UserTransactionActor], "UserTransactionActor")
    val roomTransactionActor:ActorRef = system.actorOf(Props[RoomTransactionActor],"RoomTransactionActor")
}
