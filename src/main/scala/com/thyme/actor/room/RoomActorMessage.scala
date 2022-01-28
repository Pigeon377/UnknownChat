package com.thyme.actor.room

import akka.actor.ActorRef

case class JoinRoom(uuid: Long,actorRef: ActorRef)

case class RemoveUser(uuid: Long)

case class IsUserExist(uuid:Long)

case class ChangeRoomName(newName: String)

case class BroadcastMessage(message:String)
