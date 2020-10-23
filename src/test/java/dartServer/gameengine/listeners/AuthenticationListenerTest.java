package dartServer.gameengine.listeners;

import dartServer.commons.packets.incoming.requests.AuthRequestPacket;
import dartServer.networking.events.PacketReceiveEvent;
import org.junit.jupiter.api.Test;

class AuthenticationListenerTest {

    @Test
    public void testOnAuth() {
        PacketReceiveEvent<AuthRequestPacket> event = new PacketReceiveEvent<>(null);
        AuthenticationListener authenticationListener = new AuthenticationListener();
        // TODO
    }
}