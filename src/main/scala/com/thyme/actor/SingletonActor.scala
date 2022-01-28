package com.thyme.actor

import akka.actor.{ActorRef, Props}
import com.thyme.actor.connect.WebSocketConnectActor
import com.thyme.actor.database.UserTransactionActor
import com.thyme.extension.FinalParam.system

object SingletonActor {
    val userTransactionActor: ActorRef = system.actorOf(Props[UserTransactionActor], "UserTransactionActor")
    val webSocketConnectActor:ActorRef = system.actorOf(Props[WebSocketConnectActor],"WebSocketConnectActor")
}
