package dartServer.gameengine.listeners;

import dartServer.commons.packets.incoming.requests.*;
import dartServer.commons.packets.outgoing.broadcasts.SnapshotPacket;
import dartServer.commons.packets.outgoing.unicasts.CreateGameResponsePacket;
import dartServer.gameengine.model.Game;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.Player;
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
        Player player = GameEngine.getPlayer(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyByPlayer(player);

        if (lobby.startGame(player)) {
            player.sendMessage(new CreateGameResponsePacket(true));
            player.sendMessage(new SnapshotPacket(lobby.getGame().getSnapshot()));
            logger.warn("Game " + lobby.getId() + " started");
            return;
        }
        player.sendMessage(new CreateGameResponsePacket(false));
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
        Player player = GameEngine.getPlayer(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyByPlayer(player);
        Throw t = event.getPacket().getThrow();

        if (lobby.performThrow(player, t)) {
            Game game = lobby.getGame();
            lobby.broadcastToPlayers(new SnapshotPacket(game.getSnapshot()));
            logger.warn(player.getName() + " scored " + t.toString());
        }
    }

    /**
     * @param event the event fired on undoThrow by a client
     */
    @Event
    public void onUndoThrow(PacketReceiveEvent<UndoThrowPacket> event) {
        Player player = GameEngine.getPlayer(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyByPlayer(player);

        if (lobby.undoThrow(player)) {
            Game game = lobby.getGame();
            lobby.broadcastToPlayers(new SnapshotPacket(game.getSnapshot()));
            logger.warn("Undo throw by " + player.getName());
        }
    }

}