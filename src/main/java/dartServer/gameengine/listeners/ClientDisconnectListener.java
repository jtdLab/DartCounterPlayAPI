package dartServer.gameengine.listeners;

import dartServer.gameengine.Game;
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
        logger.warn("onLeave TODO");
        for(User user : GameEngine.getUsers()) {
            logger.warn(user);
        }

        // TODO

       /*
            User user = GameEngine.getUser(event.getClient().getAddress());
        if (user.isPlayer()) {
            //Inform GameLoop that protocol was violated by User
            Lobby lobby = GameEngine.getLobbyByName(user.getLobbyName());
            if (lobby.getGameLoop() != null)
                GameEngine.getLobbyByName(user.getLobbyName()).getGameLoop().protocolViolatedBy(user);

            if (event.getClient().isKicked()) {

                Game game = GameEngine.getLobbyByName(user.getLobbyName()).getActiveGame();

                if (game.getLeftUser().getName().equals(user.getName())) {                //left player kicked
                    GameEngine.broadcastToLobby(user.getLobbyName(), new MatchFinishPacket(game.getRoundNumber(), game.getScore()[0], game.getScore()[1], game.getRightUser().getName(), VictoryReasonType.DISQUALIFICATION));
                } else {                //right player kicked
                    GameEngine.broadcastToLobby(user.getLobbyName(), new MatchFinishPacket(game.getRoundNumber(), game.getScore()[0], game.getScore()[1], game.getLeftUser().getName(), VictoryReasonType.DISQUALIFICATION));
                }
                GameEngine.removeUser(event.getClient().getAddress());

            } else {
                user.setClient(null); //remove client because disconnected
            }
        } else {
            GameEngine.removeUser(event.getClient().getAddress());
        }

        */
    }

}
