package dartServer.gameengine.listeners;

import dartServer.commons.artifacts.GameSnapshot;
import dartServer.commons.packets.incoming.requests.*;
import dartServer.commons.packets.outgoing.broadcasts.SnapshotPacket;
import dartServer.commons.packets.outgoing.unicasts.CreateGameResponsePacket;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.User;
import dartServer.gameengine.model.Throw;
import dartServer.networking.events.Event;
import dartServer.networking.events.NetworkEventListener;
import dartServer.networking.events.PacketReceiveEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Listener class for game events.
 */
public class GameListener implements NetworkEventListener {

    static final Logger logger = LogManager.getLogger(GameListener.class);

    /**
     * @param event the event fired on startGame by a client
     */
    @Event
    public void onStartGame(PacketReceiveEvent<StartGamePacket> event) {
        logger.warn("onStartGame");
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyById(user.getLobbyId());
        if(user.getName().equals(lobby.getOwnerName())) {
            if(lobby.start()) {
                user.sendMessage(new CreateGameResponsePacket(true));
                user.sendMessage(new SnapshotPacket(lobby.getActiveGame().getSnapshot()));
                return;
            }
            return;
        }
        user.sendMessage(new CreateGameResponsePacket(false));
    }

    /**
     * @param event the event fired on cancelGame by a client
     */
    @Event
    public void onCancelGame(PacketReceiveEvent<CancelGamePacket> event) {
        logger.warn("onCancelGame");
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyById(user.getLobbyId());
        if(user.getName().equals(lobby.getOwnerName())) {
           for(User u : lobby.getUsers()) {
               u.setLobbyId(-1);
           }
           GameEngine.removeLobby(lobby);
        }
    }

    /**
     * @param event the event fired on exitGame by a client
     */
    @Event
    public void onExitGame(PacketReceiveEvent<ExitGamePacket> event) {
        logger.warn("onExitGame TODO");
        // TODO
    }

    /**
     * @param event the event fired on performThrow by a client
     */
    @Event
    public void onPerformThrow(PacketReceiveEvent<PerformThrowPacket> event) {
        logger.warn("onPerformThrow");
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyById(user.getLobbyId());
        Throw t = event.getPacket().getT();

        if(user.equals(lobby.getActiveGame().getCurrentTurn())) {
            if(lobby.performThrow(t)) {
                lobby.broadcastToUsers(new SnapshotPacket(lobby.getActiveGame().getSnapshot()));
            }
        }
    }

    /**
     * @param event the event fired on undoThrow by a client
     */
    @Event
    public void onUndoThrow(PacketReceiveEvent<UndoThrowPacket> event) {
        logger.warn("onUndoThrow");
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyById(user.getLobbyId());

        if(user.equals(lobby.getActiveGame().getNextTurn())) {
            lobby.getActiveGame().undoThrow();
            lobby.broadcastToUsers(new SnapshotPacket(lobby.getActiveGame().getSnapshot()));
        }
    }

}