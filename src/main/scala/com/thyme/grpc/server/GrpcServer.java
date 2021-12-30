package com.thyme.grpc.server;

import com.thyme.grpc.ChatGrpc;
import com.thyme.grpc.Grpc;
import com.thyme.pivot.RoomPivot;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class GrpcServer extends ChatGrpc.ChatImplBase {

    private final int port;

    public GrpcServer(int port) {
        this.port = port;
    }

    public void start() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(port)
                .addService(new GrpcImpl())
                .build()
                .start();
        server.awaitTermination();
    }


    static class GrpcImpl extends ChatGrpc.ChatImplBase {
        @Override
        public void createRoom(Grpc.CreateRoomRequest request, StreamObserver<Grpc.CreateRoomResponse> responseObserver) {
            responseObserver.onNext(RoomPivot.createRoom(request));
            responseObserver.onCompleted();
        }

        @Override
        public void joinRoom(Grpc.JoinRoomRequest request, StreamObserver<Grpc.JoinRoomResponse> responseObserver) {
            responseObserver.onNext(RoomPivot.joinRoom(request));
            responseObserver.onCompleted();
        }
    }
}