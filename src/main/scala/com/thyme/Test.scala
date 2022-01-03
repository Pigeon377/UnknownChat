package com.thyme

import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import com.mongodb.client.model.Indexes
import com.thyme.actor.database.MongoTransactionActor.userCollection
import com.thyme.actor.database.{InsertUser, MongoTransactionActor}
import com.thyme.extension.Tools.system
import org.mongodb.scala.model.IndexOptions

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

object Test {

    implicit val timeout: Timeout = 10 seconds

    def start(): Unit = {
        Await.result(userCollection.createIndex(Indexes.ascending("mailbox"), IndexOptions().unique(true)).toFuture(), 10 seconds)
        val databaseActor = system.actorOf(Props[MongoTransactionActor])
        (databaseActor ? InsertUser("野兽前辈", "114514", "1919810")).onComplete(
            x => println(x)
        )
    }
}


