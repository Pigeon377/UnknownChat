package com.thyme.database

import org.mongodb.scala.{MongoClient, MongoDatabase}

class MongoExtensions {
    val mongoClient: MongoClient = MongoClient()
    val mongoDB: MongoDatabase = mongoClient.getDatabase("thyme_test")
}
