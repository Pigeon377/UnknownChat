package com.thyme.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 0.15.0)",
    comments = "Source: grpc.proto")
public class ChatGrpc {

  private ChatGrpc() {}

  public static final String SERVICE_NAME = "grpc.Chat";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.thyme.grpc.Grpc.JoinRoomRequest,
      com.thyme.grpc.Grpc.JoinRoomResponse> METHOD_JOIN_ROOM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "grpc.Chat", "JoinRoom"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.thyme.grpc.Grpc.JoinRoomRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.thyme.grpc.Grpc.JoinRoomResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.thyme.grpc.Grpc.CreateRoomRequest,
      com.thyme.grpc.Grpc.CreateRoomResponse> METHOD_CREATE_ROOM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "grpc.Chat", "CreateRoom"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.thyme.grpc.Grpc.CreateRoomRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.thyme.grpc.Grpc.CreateRoomResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatStub newStub(io.grpc.Channel channel) {
    return new ChatStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ChatBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static ChatFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ChatFutureStub(channel);
  }

  /**
   */
  @java.lang.Deprecated public static interface Chat {

    /**
     */
    public void joinRoom(com.thyme.grpc.Grpc.JoinRoomRequest request,
        io.grpc.stub.StreamObserver<com.thyme.grpc.Grpc.JoinRoomResponse> responseObserver);

    /**
     */
    public void createRoom(com.thyme.grpc.Grpc.CreateRoomRequest request,
        io.grpc.stub.StreamObserver<com.thyme.grpc.Grpc.CreateRoomResponse> responseObserver);
  }

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1469")
  public static abstract class ChatImplBase implements Chat, io.grpc.BindableService {

    @java.lang.Override
    public void joinRoom(com.thyme.grpc.Grpc.JoinRoomRequest request,
        io.grpc.stub.StreamObserver<com.thyme.grpc.Grpc.JoinRoomResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_JOIN_ROOM, responseObserver);
    }

    @java.lang.Override
    public void createRoom(com.thyme.grpc.Grpc.CreateRoomRequest request,
        io.grpc.stub.StreamObserver<com.thyme.grpc.Grpc.CreateRoomResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_ROOM, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return ChatGrpc.bindService(this);
    }
  }

  /**
   */
  @java.lang.Deprecated public static interface ChatBlockingClient {

    /**
     */
    public com.thyme.grpc.Grpc.JoinRoomResponse joinRoom(com.thyme.grpc.Grpc.JoinRoomRequest request);

    /**
     */
    public com.thyme.grpc.Grpc.CreateRoomResponse createRoom(com.thyme.grpc.Grpc.CreateRoomRequest request);
  }

  /**
   */
  @java.lang.Deprecated public static interface ChatFutureClient {

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.thyme.grpc.Grpc.JoinRoomResponse> joinRoom(
        com.thyme.grpc.Grpc.JoinRoomRequest request);

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.thyme.grpc.Grpc.CreateRoomResponse> createRoom(
        com.thyme.grpc.Grpc.CreateRoomRequest request);
  }

  public static class ChatStub extends io.grpc.stub.AbstractStub<ChatStub>
      implements Chat {
    private ChatStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatStub(channel, callOptions);
    }

    @java.lang.Override
    public void joinRoom(com.thyme.grpc.Grpc.JoinRoomRequest request,
        io.grpc.stub.StreamObserver<com.thyme.grpc.Grpc.JoinRoomResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_JOIN_ROOM, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void createRoom(com.thyme.grpc.Grpc.CreateRoomRequest request,
        io.grpc.stub.StreamObserver<com.thyme.grpc.Grpc.CreateRoomResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_ROOM, getCallOptions()), request, responseObserver);
    }
  }

  public static class ChatBlockingStub extends io.grpc.stub.AbstractStub<ChatBlockingStub>
      implements ChatBlockingClient {
    private ChatBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public com.thyme.grpc.Grpc.JoinRoomResponse joinRoom(com.thyme.grpc.Grpc.JoinRoomRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_JOIN_ROOM, getCallOptions(), request);
    }

    @java.lang.Override
    public com.thyme.grpc.Grpc.CreateRoomResponse createRoom(com.thyme.grpc.Grpc.CreateRoomRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_ROOM, getCallOptions(), request);
    }
  }

  public static class ChatFutureStub extends io.grpc.stub.AbstractStub<ChatFutureStub>
      implements ChatFutureClient {
    private ChatFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.thyme.grpc.Grpc.JoinRoomResponse> joinRoom(
        com.thyme.grpc.Grpc.JoinRoomRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_JOIN_ROOM, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.thyme.grpc.Grpc.CreateRoomResponse> createRoom(
        com.thyme.grpc.Grpc.CreateRoomRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_ROOM, getCallOptions()), request);
    }
  }

  @java.lang.Deprecated public static abstract class AbstractChat extends ChatImplBase {}

  private static final int METHODID_JOIN_ROOM = 0;
  private static final int METHODID_CREATE_ROOM = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final Chat serviceImpl;
    private final int methodId;

    public MethodHandlers(Chat serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_JOIN_ROOM:
          serviceImpl.joinRoom((com.thyme.grpc.Grpc.JoinRoomRequest) request,
              (io.grpc.stub.StreamObserver<com.thyme.grpc.Grpc.JoinRoomResponse>) responseObserver);
          break;
        case METHODID_CREATE_ROOM:
          serviceImpl.createRoom((com.thyme.grpc.Grpc.CreateRoomRequest) request,
              (io.grpc.stub.StreamObserver<com.thyme.grpc.Grpc.CreateRoomResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_JOIN_ROOM,
        METHOD_CREATE_ROOM);
  }

  @java.lang.Deprecated public static io.grpc.ServerServiceDefinition bindService(
      final Chat serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          METHOD_JOIN_ROOM,
          asyncUnaryCall(
            new MethodHandlers<
              com.thyme.grpc.Grpc.JoinRoomRequest,
              com.thyme.grpc.Grpc.JoinRoomResponse>(
                serviceImpl, METHODID_JOIN_ROOM)))
        .addMethod(
          METHOD_CREATE_ROOM,
          asyncUnaryCall(
            new MethodHandlers<
              com.thyme.grpc.Grpc.CreateRoomRequest,
              com.thyme.grpc.Grpc.CreateRoomResponse>(
                serviceImpl, METHODID_CREATE_ROOM)))
        .build();
  }
}
