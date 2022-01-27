package com.thyme.actor

import akka.actor.{ActorRef, Props}
import com.thyme.actor.connect.WebSocketConnectActor
import com.thyme.actor.database.TransactionActor
import com.thyme.extension.FinalParam.system

object SingletonActor {
    val databaseTransactionActor: ActorRef = system.actorOf(Props[TransactionActor], "DataBaseTransactionActor")
    val webSocketConnectActor:ActorRef = system.actorOf(Props[WebSocketConnectActor],"")
}
