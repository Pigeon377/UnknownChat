package com.thyme.actor.database

import akka.actor.Actor

class MongoTransactionActor extends Actor {
    override def receive: Receive = {
        case InsertUser(user) => {
            ???
        }
        case QueryUser(uuid) => ???
        case UpdateUser(user) => ???
        case _ => println("[Warning!]   Unknown Message in MongoTransactionActor receive method")
    }
}
