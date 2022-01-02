package com.thyme.actor.database

import akka.actor.Actor
import com.thyme.actor.database.MongoTransactionActor.userCollection
import com.thyme.model.User
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

// org.mongodb.scala.bson.codecs.Macros._ need be import
// otherwise fromProviders will not enjoyed implicit
// but idea will never tell you which package you should import...
class MongoTransactionActor extends Actor {
    override def receive: Receive = {
        case InsertUser(uuid,userName,password,friends) => {
            userCollection.insertOne(User(uuid = uuid, userName = userName, password = password, friends = friends))
        }
        case QueryUser(uuid) => ???
        case UpdateUser(user) => ???
        case _ => println("[Warning!]   Unknown Message in MongoTransactionActor receive method")
    }
}

object MongoTransactionActor {
    val databaseName: String = "test"
    val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[User]), DEFAULT_CODEC_REGISTRY)
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase(databaseName).withCodecRegistry(codecRegistry)
    val userCollection: MongoCollection[User] = database.getCollection("thyme")
}
