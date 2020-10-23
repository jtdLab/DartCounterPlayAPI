package dartServer.gameengine.listeners;

import dartServer.networking.events.ClientDisconnectEvent;
import org.junit.jupiter.api.Test;

class ClientDisconnectListenerTest {

    @Test
    void testOnDisconnect() {
        ClientDisconnectEvent event = new ClientDisconnectEvent(null);
        ClientDisconnectListener clientDisconnectListener = new ClientDisconnectListener();
        // TODO
    }
}