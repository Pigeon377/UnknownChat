package com.thyme.actor.database

import com.thyme.model.{Room, User}

/********************** Request ************************/
case class InsertRoom(room:Room)

case class QueryRoom(id:Long)


/********************* Response *******************************/

case class InsertRoomSucceed(roomId:Long)

case class RoomExist()

case class QueryRoomSucceed(room: Room,userList:Seq[User]=List())

case class RoomUnExist()

case class UpdateSucceed()

case class UpdateFailed()