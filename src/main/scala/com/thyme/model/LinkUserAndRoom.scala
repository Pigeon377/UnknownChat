package com.thyme.model

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.KeyedEntity
import org.squeryl.dsl.CompositeKey2

class LinkUserAndRoom(val userId: Long, val roomId: Long) extends KeyedEntity[CompositeKey2[Long, Long]] {
    override def id: CompositeKey2[Long, Long] = compositeKey(userId, roomId)
}
