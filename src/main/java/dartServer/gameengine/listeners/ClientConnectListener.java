package dartServer.gameengine.listeners;

import dartServer.networking.events.ClientConnectEvent;
import dartServer.networking.events.Event;
import dartServer.networking.events.EventPriority;
import dartServer.networking.events.NetworkEventListener;

/**
 * Listener class for the event of a new socket connection to the server opened by a client.
 */
public class ClientConnectListener implements NetworkEventListener {

    /**
     * Debug event.
     *
     * @param event the event fired on connection opening by a client
     */
    @Event
    public void onJoin(ClientConnectEvent event) {
        //event.getClient().sendPacket("Hello, nice to see you."));
    }

    /**
     * Code example for priority on events.
     *
     * @param event the event fired on connection opening by a client
     */
    @Event(priority = EventPriority.HIGHEST)
    public void onJoin2(ClientConnectEvent event) {
        // This will be fired first (before the other listener)!
    }

}

