package dartServer.gameengine.listeners;

import dartServer.networking.events.ClientConnectEvent;
import org.junit.jupiter.api.Test;

class ClientConnectListenerTest {

    @Test
    public void testOnConnect() {
        ClientConnectEvent event = new ClientConnectEvent(null);
        ClientConnectListener clientConnectListener = new ClientConnectListener();
        // TODO
    }

}