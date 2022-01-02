package com.thyme.actor.database

import com.thyme.model.User

// don't transaction with database directly!
// use case class to deliver message
// add new case class in this file when you need define a new operation in database

case class InsertUser(user: User)

case class QueryUser(uuid:Long)

case class UpdateUser(user:User)

