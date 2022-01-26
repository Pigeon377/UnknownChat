package com.thyme.actor.connect

import akka.NotUsed
import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.{Materializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import java.util.concurrent.Flow.Publisher

class WebSocketConnectActor(implicit system: ActorSystem, mat: Materializer) extends Actor {
    override def receive: Receive = {
        //TODO
        // maintain a websocket connect with user
        ???
    }

    def websocketFlow(): Flow[Message, TextMessage.Strict,Any] = {
        val (actor: ActorRef, publisher: Publisher[TextMessage.Strict])=
            Source.actorRef[String](16,OverflowStrategy.fail)
                .map(TextMessage.Strict)
                .toMat(Sink.asPublisher(false))(Keep.both).run()


    val sink:Sink[Message,Any] = Flow[Message]
        .map{
            case TextMessage.Strict(msg)=>TextMessage.Strict(msg)
        }
        .to(Sink.onComplete(
            null
        ))
    Flow.fromSinkAndSource(sink,Source.fromPublisher(publisher))
    }
}
