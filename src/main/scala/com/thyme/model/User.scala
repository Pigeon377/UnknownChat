package com.thyme.model

import org.mongodb.scala.bson.ObjectId



object User{
    def apply(uuid:Long,userName:String,password:String,friends:List[Long]): User ={
        User(_id = new ObjectId,uuid = uuid, userName = userName, password = password, friends = friends)
    }

    def apply(uuid:Long,userName:String,password:String): User ={
        User(_id = new ObjectId,uuid = uuid, userName = userName, password = password, friends = null)
    }
}

case class User(_id:ObjectId, uuid:Long, userName:String, password:String, friends: List[Long])
