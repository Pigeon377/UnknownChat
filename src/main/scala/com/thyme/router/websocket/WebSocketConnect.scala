package com.thyme.router.websocket

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives.{handleWebSocketMessages, path}
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.stream.{Materializer, OverflowStrategy}
import org.reactivestreams.Publisher

object WebSocketConnect {
    def controller(implicit system: ActorSystem, mat: Materializer): Route = {
        path("chat") {
            handleWebSocketMessages(websocketFlow)
        }
    }

    private[this] def websocketFlow()(implicit system: ActorSystem, mat: Materializer): Flow[Message, TextMessage.Strict, Any] = {
        val (actor: ActorRef, publisher: Publisher[TextMessage.Strict]) =
            Source.actorRef[String](16, OverflowStrategy.fail)
                .map(TextMessage.Strict)
                .toMat(Sink.asPublisher(false))(Keep.both).run()

        

        val sink: Sink[Message, Any] = Flow[Message]
            .map {
                case TextMessage.Strict(msg) => TextMessage.Strict(msg)
            }
            .to(Sink.onComplete(
                null
            ))
        Flow.fromSinkAndSource(sink, Source.fromPublisher(publisher))
    }
}
