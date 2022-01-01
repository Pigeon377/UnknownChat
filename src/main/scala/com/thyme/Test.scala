package com.thyme

import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.types.ObjectId
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.{IndexOptions, Indexes}
import org.mongodb.scala.{Document, MongoClient}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object Test {
    def start(): Unit = {
        val codecRegistry = fromRegistries(fromProviders(classOf[Person]), DEFAULT_CODEC_REGISTRY)
        val mongoClient = MongoClient()
        val database = mongoClient.getDatabase("test").withCodecRegistry(codecRegistry)
        val collection = database.getCollection("thyme")
        Await.result(
        collection.createIndex(Indexes.ascending("uuid"),IndexOptions().unique(true)).toFuture()
        ,10 seconds)
        //        val person = Person("114514", 1919810)
        // !must use sync method ,otherwise cant finish any operation!
        //        println(Await.result(collection.insertOne(person).toFuture(), 10 seconds))

        Await.result(collection.insertOne(Document(
            "_id" -> new ObjectId,
            "uuid"->114514,
            "name" -> "野兽前辈",
            "age" -> 1919810
        )).toFuture(), 7 second)
        Await.result(collection.find().toFuture(), 7 seconds).foreach(println)
    }
}


object Person {
    def apply(name: String, age: Int): Person = {
        Person(new ObjectId(), name, age)
    }
}

case class Person(_id: ObjectId, name: String, age: Int)
