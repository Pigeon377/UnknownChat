package com.thyme.router.websocket

/**
 * @param code => operator code,
 *             it must follow operator code rulers
 *             otherwise, connect will be closed
 *             ruler:
 *             if code equals
 *                  0 => join all rooms user used to joined
 *                  1 => join special room with roomId
 *                  2 => Future impl
 *                  3 => send normal message to special room (message will be broadcast in this room)(user must in this room)
 *
 * @param roomId => room id :p
 *
 * @param token  => jwt token, identify (may be need too much cpu time)
 *
 *
 * */
case class WebSocketConnectRequestMessage(token:String,code:Int,roomId:Long,msg:String)

case class WebSocketConnectResponseMessage(roomId:Long,roomName:String,msg:String)