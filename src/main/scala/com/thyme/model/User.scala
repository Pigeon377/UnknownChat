package com.thyme.model

case class User(uuid:Long, userName:String, password:String,friends: List[Long])
