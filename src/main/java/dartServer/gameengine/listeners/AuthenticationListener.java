package dartServer.gameengine.listeners;

import dartServer.commons.packets.incoming.requests.AuthRequestPacket;
import dartServer.commons.packets.outgoing.unicasts.AuthResponsePacket;
import dartServer.networking.events.Event;
import dartServer.networking.events.NetworkEventListener;
import dartServer.networking.events.PacketReceiveEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Listener class for the event of a new authRequest by a client.
 */
public class AuthenticationListener implements NetworkEventListener {

    static final Logger logger = LogManager.getLogger(AuthenticationListener.class);

    /**
     * Debug event.
     *
     * @param event the event fired on authRequest by a client
     */
    @Event
    public void onAuth(PacketReceiveEvent<AuthRequestPacket> event) {
        logger.warn("onAuth");
    }

}
