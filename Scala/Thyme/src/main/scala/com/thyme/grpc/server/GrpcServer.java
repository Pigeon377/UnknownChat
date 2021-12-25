package com.thyme.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class GrpcServer extends ChatServiceGrpc.ChatServiceImplBase {

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


    static class GrpcImpl extends ChatServiceGrpc.ChatServiceImplBase {
        @Override
        public void communicate(Grpc.RequestMessage request, StreamObserver<Grpc.ResponseMessage> responseObserver) {
            System.out.println("body: " + request.getBody() + "\nsender: " + request.getSender() + "\nreceiver: " + request.getReceiver() + "\n");
            Grpc.ResponseMessage res = Grpc.ResponseMessage
                    .newBuilder()
                    .setRoomID(114514)
                    .setStatus(true)
                    .build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        }
    }
}