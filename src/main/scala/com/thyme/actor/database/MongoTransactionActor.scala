package com.thyme.actor.database

import akka.actor.Actor
import com.mongodb.client.model.Indexes
import com.thyme.actor.database.MongoTransactionActor.{executor, userCollection}
import com.thyme.model.User
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.{Filters, IndexOptions}
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase, Observable}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

// org.mongodb.scala.bson.codecs.Macros._ need be import
// otherwise fromProviders will not enjoyed implicit
// but IDEA will never tell you which package you should import...
class MongoTransactionActor extends Actor {

    override def receive: Receive = {
        /**
         * @return (to sender)
         *         succeed insert user => true
         *         failed insert user => false
         * */
        case InsertUser(user) =>
            executor(userCollection.find(equal("mailbox", user.mailbox)).first()) match {
                case List() => // can't find this document
                    executor(userCollection.insertOne(user))
                    sender() ! InsertSucceed()
                case _ => sender() ! UserExist()
            }

        /**
         * @return (to sender)
         *         succeed query user => this user
         *         failed query this user or can't find this user message => null
         * */
        case QueryUser(mailbox) =>
            executor(userCollection.find(equal("mailbox", mailbox)).first()) match {
                case List() => sender() ! UserUnExist()
                case userList => sender() ! QuerySucceed(userList.head)
            }
        case UpdateUser(user, operatorCode) =>
            executor(userCollection.find(equal("mailbox", user.mailbox)).first()) match {
                case List() => sender() ! UserUnExist()
                case userList =>
                    val userFromDatabase = userList.head
                    val replaceUser = User(
                        _id = userFromDatabase._id,
                        userName = if (operatorCode == 0) user.userName else userFromDatabase.userName,
                        mailbox = userFromDatabase.mailbox,
                        password = if (operatorCode == 1) user.password else userFromDatabase.password,
                        friends = userFromDatabase.friends,
                        rooms = userFromDatabase.rooms
                    )
                    userCollection.replaceOne(equal("mailbox", ""), replaceUser).subscribe(res =>
                        if (res.wasAcknowledged()) {
                            sender() ! UpdateSucceed()
                        } else {
                            sender() ! UpdateFailed()
                        })
            }
        case _ => println("[Warning!]   Unknown Message in MongoTransactionActor receive method")
    }
}

object MongoTransactionActor {
    val databaseName: String = "test"
    val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[User]), DEFAULT_CODEC_REGISTRY)
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase(databaseName).withCodecRegistry(codecRegistry)
    val userCollection: MongoCollection[User] = database.getCollection("user")
    // create unique index
    Await.result(userCollection.createIndex(Indexes.ascending("mailbox"), IndexOptions().unique(true)).toFuture(), 10 seconds)

    private def executor[T](observable: Observable[T]): Seq[T] = {
        Await.result(observable.toFuture(), 7 seconds)
        // only query by mailbox
        // so we only have unique user object
    }
}
