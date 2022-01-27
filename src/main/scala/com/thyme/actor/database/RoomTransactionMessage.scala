package com.thyme.actor.database

import com.thyme.model.Room

/********************** Request ************************/
case class InsertRoom(room:Room)

case class QueryRoom(id:Long)


/********************* Response *******************************/

case class InsertSucceed(room:Room)

case class RoomExist()

case class QuerySucceed(room: Room)

case class RoomUnExist()

case class UpdateSucceed()

case class UpdateFailed()