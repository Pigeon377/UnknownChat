package com.thyme.router.websocket

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives.{handleWebSocketMessages, path}
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.stream.{Materializer, OverflowStrategy}
import com.thyme.actor.room.{BroadcastMessage, JoinRoom, RoomActor}
import org.reactivestreams.Publisher
import spray.json.DefaultJsonProtocol.{IntJsonFormat, LongJsonFormat, StringJsonFormat, jsonFormat4}
import spray.json.{JsonParser, RootJsonFormat}
import com.thyme.extension.ExtensionFunction._

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

        // any exception all make this program break
        // and close connection with user
        val sink: Sink[Message, Any] = Flow[Message]
            .map {
                case TextMessage.Strict(msg) =>
                    val request = JsonParser(msg).convertTo[WebSocketConnectRequestMessage]
                    val userId = checkJwtToken(request.token)
                    if (userId == -1) {
                        throw new Exception(s"$userId is not exist")
                    }
                    request.code match {
                        case 0 => RoomActor.joinAllRoomWithUserId(userId, actor)
                        case 1 => RoomActor(request.roomId) ! JoinRoom(userId, actor)
                        case 3 => RoomActor(request.roomId) ! BroadcastMessage(request.msg)
                        case x => throw new Exception(s"Unknown Operator Code $x"); null
                    }
            }
            .to(Sink.onComplete(
                null
            ))
        Flow.fromSinkAndSource(sink, Source.fromPublisher(publisher))
    }

    private implicit val jsonConvert: RootJsonFormat[WebSocketConnectRequestMessage] = jsonFormat4(WebSocketConnectRequestMessage)

}
