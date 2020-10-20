package dartServer.gameengine.listeners;

import dartServer.commons.packets.incoming.requests.CreateGamePacket;
import dartServer.commons.packets.incoming.requests.JoinGamePacket;
import dartServer.commons.packets.outgoing.broadcasts.SnapshotPacket;
import dartServer.commons.packets.outgoing.unicasts.CreateGameResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.JoinGameResponsePacket;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.Player;
import dartServer.networking.events.Event;
import dartServer.networking.events.NetworkEventListener;
import dartServer.networking.events.PacketReceiveEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Listener class for server events.
 */
public class ServerListener implements NetworkEventListener {

    static final Logger logger = LogManager.getLogger(ServerListener.class);

    /**
     * @param event the event fired on createGame by a client
     */
    @Event
    public void onCreateGame(PacketReceiveEvent<CreateGamePacket> event) {
        Player player = GameEngine.getPlayer(event.getClient().getAddress());
        Lobby lobby = GameEngine.createLobby(player);

        if(lobby != null) {
            player.sendMessage(new CreateGameResponsePacket(true));
            player.sendMessage(new SnapshotPacket(lobby.getGame().getSnapshot()));
            logger.warn(player.getName() + " created lobby " + lobby.getId() + "[Code = " + lobby.getCode() + "]");
        } else {
            player.sendMessage(new CreateGameResponsePacket(false));
        }
    }

    /**
     * @param event the event fired on joinGame by a client
     */
    @Event
    public void onJoinGame(PacketReceiveEvent<JoinGamePacket> event) {
        Player player = GameEngine.getPlayer(event.getClient().getAddress());
        int code = event.getPacket().getGameCode();
        Lobby lobby = GameEngine.joinLobby(player, code);

        if(lobby != null) {
            player.sendMessage(new JoinGameResponsePacket(true));
            lobby.broadcastToPlayers(new SnapshotPacket(lobby.getGame().getSnapshot()));
            logger.warn(player.getName() + " joined lobby " + lobby.getId() + "[Code = " + lobby.getCode() + "]");
        } else {
            player.sendMessage(new JoinGameResponsePacket(false));
        }
    }

}