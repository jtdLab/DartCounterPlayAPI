package helpers;

import dartServer.commons.packets.Packet;
import dartServer.commons.packets.PacketContainer;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestClient {

    private int port;

    private WebSocketClient client;
    private SimpleWebSocket socket;

    public TestClient(int port) {
        this.port = port;
    }

    public void connect() {
        String destUri = "ws://127.0.0.1:" + port;
        client = new WebSocketClient();
        socket = new SimpleWebSocket();

        try {
            client.start();
            URI uri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, uri, request).get(2, TimeUnit.SECONDS);
            System.out.printf("Connecting to : %s%n", uri);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            socket.awaitClose(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void send(Packet packet) {
        socket.send(packet);
    }

    public PacketContainer getLastReceived() {
        return socket.getLastReceived();
    }

    public List<PacketContainer> getReceived() {
        return socket.getReceived();
    }
}
