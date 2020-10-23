package dartServer.gameengine.listeners;

import dartServer.api.AuthService;
import dartServer.commons.packets.incoming.requests.AuthRequestPacket;
import dartServer.commons.packets.outgoing.unicasts.AuthResponsePacket;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.lobby.Player;
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
        AuthRequestPacket authRequest = event.getPacket();
        boolean authenticated = AuthService.authenticate(authRequest);

        if (authenticated) {
            Player player = GameEngine.createPlayer(authRequest.getUsername(), event.getClient());
            if (player != null) {
                player.sendMessage(new AuthResponsePacket(true));
                logger.warn(authRequest.getUsername() + " joined");
            } else {
                event.getClient().sendPacket(new AuthResponsePacket(false));
            }
        } else {
            event.getClient().sendPacket(new AuthResponsePacket(false));
        }
    }

}
