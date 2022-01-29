package com.thyme.actor.room

import akka.actor.ActorRef

case class JoinRoom(uuid: Long,actorRef: ActorRef)

case class LeftRoom(uuid:Long)


case class ChangeName(newName: String)

case class BroadcastMessage(message:String)
