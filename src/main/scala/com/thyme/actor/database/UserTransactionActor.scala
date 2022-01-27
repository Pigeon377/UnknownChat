package com.thyme.actor.database

import akka.actor.Actor
import com.thyme.model.{DataBase, User}
import org.squeryl.PrimitiveTypeMode._

import scala.language.postfixOps

class UserTransactionActor extends Actor {
    override def receive: Receive = {

        case InsertUser(user) => {
            transaction {
                if (DataBase.users.where(x => x.mailbox === user.mailbox).isEmpty) {
                    DataBase.users.insert(user)
                    sender() ! InsertUserSucceed(DataBase.users.where(x => x.mailbox === user.mailbox).head.id)
                } else {
                    sender() ! UserExist()
                }
            }
        }

        case QueryUser(id) =>
            transaction {
                val user: User = DataBase.users.where(x => x.id === id).head
                if (user == null) {
                    sender() ! UserUnExist()
                } else {
                    sender() ! QuerySucceed(user)
                }
            }

        case UpdateUser(user) => ???

        case _ => println("[Warning!]   Unknown Message in MongoTransactionActor receive method")
    }
}

