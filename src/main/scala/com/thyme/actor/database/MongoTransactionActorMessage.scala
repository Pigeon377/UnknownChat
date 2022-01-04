package com.thyme.actor.database

import com.thyme.model.User

// don't transaction with database directly!
// use case class to deliver message
// add new case class in this file when you need define a new operation in database

/************************Request**************************/
case class InsertUser(mailbox:String,userName:String,password:String)

case class QueryUser(mailbox:String)

case class UpdateUser(mailbox:String,userName:String,password:String,friends:List[Long],rooms:List[Long])


/************************Response************************/
case class InsertSucceed()

case class UserExist()

case class QuerySucceed(user: User)

case class UserUnExist()
