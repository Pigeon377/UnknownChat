package com.thyme.model

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{Schema, Table}

object DataBase extends Schema {
    val users: Table[User] = table[User]
    val rooms: Table[Room] = table[Room]
    val chatMessages: Table[ChatMessage] = table[ChatMessage]

    on(users)(user => declare(
        user.id is(primaryKey, autoIncremented),
        user.mailbox is unique
    ))

    on(rooms)(room => declare(
        room.id is(primaryKey, autoIncremented),
        room.name is indexed
    ))

    on(chatMessages)(chatMessage => declare(
        chatMessage.id is(primaryKey, autoIncremented)
    ))

    val linkUserAndRoom: ManyToManyRelationImpl[User, Room, LinkUserAndRoom] = manyToManyRelation(users, rooms).via[LinkUserAndRoom](
        (u, r, ur) => (u.id === ur.userId, r.id === ur.roomId)
    )

    val roomToChatMessage: OneToManyRelationImpl[Room, ChatMessage] =
        oneToManyRelation(rooms, chatMessages).via((r, c) => r.id === c.roomId)

}


