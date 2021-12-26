package com.thyme.pivot

import com.thyme.grpc.Grpc.{CreateRoomRequest, CreateRoomResponse, JoinRoomRequest, JoinRoomResponse}

object RoomPivot {

    def joinRoom(request: JoinRoomRequest): JoinRoomResponse = {
        JoinRoomResponse.newBuilder()
            .setStatus()
            .setRoomName()
            .setRoomWebsocketURL()
            .setUserList()
            .build()
    }

    def createRoom(request: CreateRoomRequest): CreateRoomResponse = {
        CreateRoomResponse.newBuilder()
            .setStatus()
            .setRoomName()
            .setRoomWebsocketURL()
            .setUserList()
            .build()
    }

}
