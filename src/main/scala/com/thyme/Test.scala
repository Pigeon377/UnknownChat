package com.thyme

import akka.util.Timeout
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.ObjectId

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.{Duration, DurationInt}
import scala.language.postfixOps

object Test {
    def start(): Unit = {
        val codecRegistry = fromRegistries(fromProviders(classOf[Person]), DEFAULT_CODEC_REGISTRY)
        val mongoClient = MongoClient()
        val database = mongoClient.getDatabase("test").withCodecRegistry(codecRegistry)
        val collection: MongoCollection[Person] = database.getCollection("thyme")
        val person = Person("114514", 1919810)
        // !must use sync method ,otherwise cant finish any operation!
        println(Await.result(collection.insertOne(person).toFuture(), 10 seconds))
        collection.find().first().map(x => println(x))

    }
}


object Person {
    def apply(name: String, age: Int): Person = {
        Person(new ObjectId(), name, age)
    }
}

case class Person(_id: ObjectId, name: String, age: Int)
