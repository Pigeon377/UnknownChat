package com.thyme.actor.room

case class AddNewUser(uuid: Long)

case class RemoveUser(uuid: Long)

case class IsUserExist(uuid:Long)

case class ChangeRoomName(newName: String)

case class BroadcastMessage(message:String)
