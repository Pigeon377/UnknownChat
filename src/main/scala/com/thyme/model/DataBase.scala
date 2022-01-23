package com.thyme.model

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{Schema, Session, SessionFactory, Table}

object DataBase extends Schema {
    val user: Table[User] = table[User]
    val room: Table[Room] = table[Room]
    val chatMessage: Table[ChatMessage] = table[ChatMessage]

    on(user)(user => declare(
        user.id is (primaryKey, autoIncremented),
        user.mailbox is unique
    ))

    on(room)(room=>declare(
        room.id is (primaryKey, autoIncremented),
        room.name is indexed
    ))

    on(chatMessage)(chatMessage=>declare(
        chatMessage.id is (primaryKey, autoIncremented)
    ))

}
