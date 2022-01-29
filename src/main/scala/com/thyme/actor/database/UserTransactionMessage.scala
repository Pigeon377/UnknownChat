package com.thyme.actor.database

import com.thyme.model.User

// don't transaction with database directly!
// use case class to deliver message
// add new case class in this file when you need define a new operation in database

/** **********************Request************************* */
case class InsertUser(user: User)

case class QueryUser(id: Long)

case class UpdateUser(user: User)

case class QueryUserJoinedAllRoom(id: Long)

/** **********************Response*********************** */
case class InsertUserSucceed(userId: Long)

case class UserExist()

case class QueryUserSucceed(user: User)

case class UserUnExist()

case class UpdateUserSucceed()

case class UpdateUserFailed()

case class QueryUserJoinedAllRoomSucceed(roomIdList:List[Long]=List())
