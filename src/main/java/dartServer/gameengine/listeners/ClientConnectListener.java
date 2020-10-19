package dartServer.gameengine.listeners;

import com.google.gson.Gson;
import dartServer.commons.packets.incoming.requests.*;
import dartServer.commons.packets.outgoing.unicasts.AuthResponsePacket;
import dartServer.gameengine.model.Throw;
import dartServer.networking.events.ClientConnectEvent;
import dartServer.networking.events.Event;
import dartServer.networking.events.EventPriority;
import dartServer.networking.events.NetworkEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Listener class for the event of a new socket connection to the server opened by a client.
 */
public class ClientConnectListener implements NetworkEventListener {

    static final Logger logger = LogManager.getLogger(ClientConnectListener.class);

    /**
     * Debug event.
     *
     * @param event the event fired on connection opening by a client
     */
    @Event
    public void onConnect(ClientConnectEvent event) {
      logger.warn("onConnect");
    }

}

