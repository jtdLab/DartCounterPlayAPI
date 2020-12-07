package dartServer.gameengine.listeners;

import dartServer.gameengine.GameEngine;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.User;
import dartServer.networking.events.ClientDisconnectEvent;
import dartServer.networking.events.Event;
import dartServer.networking.events.NetworkEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Listener class for the event of a socket connection being closed by client or server.
 */
public class ClientDisconnectListener implements NetworkEventListener {

    static final Logger logger = LogManager.getLogger(ClientDisconnectListener.class);

    /**
     * Event called on a disconnect event. Will be called regardless of who closed the connection.
     * If the user is an active player and loses the connection client-side, the match will remain.
     * If the server closes the connection to an active player (kick), the match is ended and the other player awarded the victory.
     *
     * @param event
     */
    @Event
    public void onDisconnect(ClientDisconnectEvent event) {
        User user = GameEngine.getUser(event.getClient().getAddress());

        if (user == null) {
            logger.trace(event.getClient().getAddress() + " disconnected");
            return;
        }

        if (user.isPlaying()) {
            if (event.getClient().isKicked()) {
                GameEngine.removeUser(user);
                logger.warn(user.getUsername() + " got kicked");
            } else {
                user.setClient(null); // remove client because disconnected
                logger.warn(user.getUsername() + " left");
            }
        } else {
            GameEngine.removeUser(user);
            logger.warn(user.getUsername() + " left");
        }
    }

}
