package com.thyme.router.chat

import akka.NotUsed
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.{Flow, Source}

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

object ChatSession {

    def controller(implicit ec: ExecutionContext): Route = {
        path("chat") {
            handleWebSocketMessages(websocketFlow)
        }
    }

    def websocketFlow: Flow[Message, TextMessage.Strict, NotUsed] ={
        Flow[Message] map {
            case TextMessage.Strict(text) => TextMessage.Strict(text)
        }
    }

}

class ChatSession{
    def dataStream:Source[Message,_] = ???
}
