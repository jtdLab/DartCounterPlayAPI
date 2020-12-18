package dartServer.gameengine.listeners;

import dartServer.commons.packets.incoming.requests.*;
import dartServer.commons.packets.outgoing.broadcasts.SnapshotPacket;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.User;
import dartServer.gameengine.model.Game;
import dartServer.gameengine.model.GameConfig;
import dartServer.gameengine.model.Throw;
import dartServer.networking.events.Event;
import dartServer.networking.events.NetworkEventListener;
import dartServer.networking.events.PacketReceiveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener class for game events.
 */
public class GameListener implements NetworkEventListener {

    static final Logger logger = LoggerFactory.getLogger(GameListener.class);

    /**
     * @param event the event fired on onGameConfigUpdate by a client
     */
    @Event
    public void onUpdateGameConfig(PacketReceiveEvent<UpdateGameConfigPacket> event) {
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyByUser(user);
        GameConfig config = event.getPacket().getGameConfig();

        if (lobby.updateGameConfig(user, config)) {
            Game game = lobby.getGame();
            SnapshotPacket snapshotPacket = new SnapshotPacket(game.getSnapshot());
            lobby.broadcastToUsers(snapshotPacket);
            logger.info(user.getUsername() + " updated gameConfig of lobby " + lobby.getId() + " [Code = " + lobby.getCode() + "]");
        }
    }

    /**
     * @param event the event fired on startGame by a client
     */
    @Event
    public void onStartGame(PacketReceiveEvent<StartGamePacket> event) {
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyByUser(user);

        if (lobby.startGame(user)) {
            lobby.broadcastToUsers(new SnapshotPacket(lobby.getGame().getSnapshot()));
            logger.info("Game " + lobby.getId() + " started");
            return;
        }
    }

    /**
     * @param event the event fired on cancelGame by a client
     */
    @Event
    public void onCancelGame(PacketReceiveEvent<CancelGamePacket> event) {
        logger.warn("onCancelGame - NOT IMPLEMENTED YET");
        // TODO
        /*Player player = GameEngine.getPlayer(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyByPlayer(player.getLobbyId());
        if (player.getName().equals(lobby.getOwnerName())) {
            for (Player u : lobby.getPlayers()) {
                u.setLobbyId(-1);
            }
            GameEngine.removeLobby(lobby);
            logger.warn("Game " + lobby.getId() + " canceled");
            // TODO send msg
        }*/
    }

    /**
     * @param event the event fired on exitGame by a client
     */
    @Event
    public void onExitGame(PacketReceiveEvent<ExitGamePacket> event) {
        logger.warn("onExit - NOT IMPLEMENTED YET");
        // TODO
    }

    /**
     * @param event the event fired on performThrow by a client
     */
    @Event
    public void onPerformThrow(PacketReceiveEvent<PerformThrowPacket> event) {
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyByUser(user);
        Throw t = event.getPacket().getThrow();

        if (lobby.performThrow(user, t)) {
            Game game = lobby.getGame();
            lobby.broadcastToUsers(new SnapshotPacket(game.getSnapshot()));
            logger.info(user.getUsername() + " scored " + t.toString());
        }
    }

    /**
     * @param event the event fired on undoThrow by a client
     */
    @Event
    public void onUndoThrow(PacketReceiveEvent<UndoThrowPacket> event) {
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyByUser(user);

        if (lobby.undoThrow(user)) {
            Game game = lobby.getGame();
            lobby.broadcastToUsers(new SnapshotPacket(game.getSnapshot()));
            logger.info("Undo throw by " + user.getUsername());
        }
    }

}