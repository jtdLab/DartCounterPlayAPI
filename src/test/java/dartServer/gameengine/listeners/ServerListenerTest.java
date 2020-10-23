package dartServer.gameengine.listeners;

import dartServer.commons.packets.incoming.requests.CreateGamePacket;
import dartServer.commons.packets.incoming.requests.JoinGamePacket;
import dartServer.networking.events.PacketReceiveEvent;
import org.junit.jupiter.api.Test;

class ServerListenerTest {

    @Test
    void testOnCreateGame() {
        PacketReceiveEvent<CreateGamePacket> event = new PacketReceiveEvent<>(null);
        ServerListener serverListener = new ServerListener();
        // TODO
    }

    @Test
    void test0nJoinGame() {
        PacketReceiveEvent<JoinGamePacket> event = new PacketReceiveEvent<>(null);
        ServerListener serverListener = new ServerListener();
        // TODO
    }
}