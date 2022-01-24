package com.thyme.actor.database

import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.MySQLAdapter

object TransactionConnection {

    def startDatabaseSession(): Unit = {

        val databaseUsername = "root"
        val databasePassword = "3777777"
        val databaseConnection = "jdbc:mysql://localhost:3306/thyme"

        Class.forName("com.mysql.cj.jdbc.Driver")
        SessionFactory.concreteFactory = Some(() => Session.create(
            java.sql.DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword),
            new MySQLAdapter
        ))


    }

    startDatabaseSession()  // connect when init (maybe?)

}
