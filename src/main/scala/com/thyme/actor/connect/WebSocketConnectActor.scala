package com.thyme.actor.connect

import akka.NotUsed
import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.{Materializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import java.util.concurrent.Flow.Publisher

class WebSocketConnectActor() extends Actor {
    override def receive: Receive = {
        //TODO
        // maintain a websocket connect with user
        ???
    }





}
