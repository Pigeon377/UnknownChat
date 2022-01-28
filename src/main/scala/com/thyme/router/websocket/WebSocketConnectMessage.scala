package com.thyme.router.websocket

/**
 * @param code => operator code,
 *             it must follow operator code rulers
 *             otherwise, connect will be closed
 *             ruler:
 *             if code equals
 *                  1 =>
 *                  2 =>
 *                  3 =>
 * @param
 * */
case class WebSocketConnectMessage(code:Int)

