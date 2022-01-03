package com.thyme

import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import com.thyme.actor.database.{InsertUser, MongoTransactionActor, QueryUser}
import com.thyme.extension.Tools.system
import org.bson.BSONObject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object Test {

    implicit val timeout: Timeout = 10 seconds

    def start(): Unit = {
        val databaseActor = system.actorOf(Props[MongoTransactionActor])
        (databaseActor ? QueryUser( "114514")).onComplete(
            x => println(x.get)
        )
    }
}


