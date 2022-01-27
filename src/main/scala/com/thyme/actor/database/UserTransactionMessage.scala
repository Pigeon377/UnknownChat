package com.thyme.actor.database

import com.thyme.model.User

// don't transaction with database directly!
// use case class to deliver message
// add new case class in this file when you need define a new operation in database

/** **********************Request************************* */
case class InsertUser(user: User)

case class QueryUser(id: Long)

// operatorCode =>
//  0 => update password
//  1 => update username
case class UpdateUser(user: User)


/** **********************Response*********************** */
case class InsertSucceed(user:User)

case class UserExist()

case class QuerySucceed(user: User)

case class UserUnExist()

case class UpdateSucceed()

case class UpdateFailed()