package com.thyme.actor.database

import akka.actor.Actor
import com.thyme.actor.database.MongoTransactionActor.userCollection
import com.thyme.model.User
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase, Observable}

import scala.concurrent.Await
import scala.concurrent.Await.result
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

// org.mongodb.scala.bson.codecs.Macros._ need be import
// otherwise fromProviders will not enjoyed implicit
// but IDEA will never tell you which package you should import...
class MongoTransactionActor extends Actor {
    override def receive: Receive = {

        case InsertUser(userName, mailbox, password) => {
            userCollection.find()
            val res = Await.result(
                userCollection.insertOne(
                    User(userName = userName, mailbox: String, password = password))
                    .toFuture(), 7 seconds)
            sender() ! res.getInsertedId
        }
        case QueryUser(uuid) => ???
        case UpdateUser(uuid, userName, password, friends) => ???
        case _ => println("[Warning!]   Unknown Message in MongoTransactionActor receive method")
    }
}

object MongoTransactionActor {
    val databaseName: String = "test"
    val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[User]), DEFAULT_CODEC_REGISTRY)
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase(databaseName).withCodecRegistry(codecRegistry)
    val userCollection: MongoCollection[User] = database.getCollection("thyme")

    private def executor[T](observable: Observable[T]) = {
        result(observable.toFuture(), 10 seconds)
    }
}
