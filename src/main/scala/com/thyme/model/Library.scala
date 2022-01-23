package com.thyme.model

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{Schema, Session, SessionFactory, Table}

object Library extends Schema {
    val user: Table[User] = table[User]
    on(user)(user => declare(
        user.uuid is primaryKey,
        user.userName is indexed
    ))
}
