package com.thyme.pivot

import com.thyme.actor.room.RoomActor
import com.thyme.grpc.Grpc.{CreateRoomRequest, CreateRoomResponse, JoinRoomRequest, JoinRoomResponse}

object RoomPivot {

    def joinRoom(request: JoinRoomRequest): JoinRoomResponse = {
        if (RoomActor.isRoomExist(request.getRoomID)){
            // TODO
            // fix logic
            // we can use null judge instead of exist judge
            JoinRoomResponse
                .newBuilder()
                .setStatus(true)
                .setRoomName("下北泽")
                .setRoomWebsocketURL("")
//                .setUserList()
                .build()
        }else{
            JoinRoomResponse
                .newBuilder()
                .setStatus(true)
                .setRoomName("下北泽")
                .setRoomWebsocketURL("")
//                .setUserList()
                .build()
        }

    }

    def createRoom(request: CreateRoomRequest): CreateRoomResponse = {

        CreateRoomResponse.newBuilder()
            .setStatus(true)
            .setRoomName("")
            .setRoomWebsocketURL("")
//            .setUserList()
            .build()
    }

}
