package com.thyme.router.chat

import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Flow

import scala.language.postfixOps

object ChatSession {
    def controller(): Route = {
        path("chat") {
            handleWebSocketMessages(Flow[Message] map {
                case TextMessage.Strict(text) => TextMessage.Strict(text)
            })
        }
    }
}
