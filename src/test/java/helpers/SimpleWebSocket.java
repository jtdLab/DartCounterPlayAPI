package helpers;//
//  ========================================================================
//  Copyright (c) 1995-2020 Mort Bay Consulting Pty Ltd and others.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import dartServer.networking.artefacts.Container;
import dartServer.networking.handlers.codec.ContainerDecoder;
import dartServer.networking.handlers.codec.ContainerEncoder;
import dartServer.networking.artefacts.Packet;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;


@org.eclipse.jetty.websocket.api.annotations.WebSocket(maxTextMessageSize = 64 * 1024)
public class SimpleWebSocket
{
    private Container lastReceived;
    private List<Container> received;

    private final CountDownLatch closeLatch;
    private Session session;

    public SimpleWebSocket() {
       this.closeLatch = new CountDownLatch(1);
       received = new ArrayList<>();
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
        this.session = null;
        this.closeLatch.countDown(); // trigger latch
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("Got connect: %s%n", session);
        this.session = session;
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        System.out.printf("Got msg: %s%n", msg);
        Container container = ContainerDecoder.decode(msg);
        received.add(container);
        lastReceived = container;
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        System.out.printf("Got msg: %s%n", cause.getMessage());
    }


    public void send(Packet packet) {
        try {
            String msg = ContainerEncoder.encode(packet);
            Future<Void> fut = session.getRemote().sendStringByFuture(msg);
            fut.get(2, TimeUnit.SECONDS); // wait for send to complete.
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    public Container getLastReceived() {
        return lastReceived;
    }

    public List<Container> getReceived() {
        return received;
    }

}
