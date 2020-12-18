package dartServer.gameengine.listeners;

import dartServer.api.services.InvitationService;
import dartServer.commons.packets.incoming.requests.CreateGamePacket;
import dartServer.commons.packets.incoming.requests.InviteToGamePacket;
import dartServer.commons.packets.incoming.requests.JoinGamePacket;
import dartServer.commons.packets.outgoing.broadcasts.SnapshotPacket;
import dartServer.commons.packets.outgoing.unicasts.CreateGameResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.InviteToGameResponse;
import dartServer.commons.packets.outgoing.unicasts.JoinGameResponsePacket;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.User;
import dartServer.networking.events.Event;
import dartServer.networking.events.NetworkEventListener;
import dartServer.networking.events.PacketReceiveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener class for server events.
 */
public class ServerListener implements NetworkEventListener {

    static final Logger logger = LoggerFactory.getLogger(ServerListener.class);

    /**
     * @param event the event fired on createGame by a client
     */
    @Event
    public void onCreateGame(PacketReceiveEvent<CreateGamePacket> event) {
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.createLobby(user);

        if (lobby != null) {
            user.sendMessage(new CreateGameResponsePacket(true, lobby.getGame().getSnapshot()));
            logger.info(user.getUsername() + " created lobby " + lobby.getId() + " [Code = " + lobby.getCode() + "]");
        } else {
            user.sendMessage(new CreateGameResponsePacket(false, null));
        }
    }

    /**
     * @param event the event fired on createGame by a client
     */
    @Event
    public void onInviteToGame(PacketReceiveEvent<InviteToGamePacket> event) {
        InviteToGamePacket invite = event.getPacket();
        User user = GameEngine.getUser(event.getClient().getAddress());
        Lobby lobby = GameEngine.getLobbyByUser(user);

        if (lobby != null) {
            user.sendMessage(new InviteToGameResponse(true));
            InvitationService.addInvitation(invite.getUid(), user.getUsername(), lobby.getCode());
            // TODO check if game is pending
            logger.info(user.getUsername() + " invited " + invite.getUsername() + " to lobby " + lobby.getId() + "[Code = " + lobby.getCode() + "]");
        } else {
            user.sendMessage(new InviteToGameResponse(false));
        }
    }


    /**
     * @param event the event fired on joinGame by a client
     */
    @Event
    public void onJoinGame(PacketReceiveEvent<JoinGamePacket> event) {
        User user = GameEngine.getUser(event.getClient().getAddress());
        int code = event.getPacket().getGameCode();
        Lobby lobby = GameEngine.joinLobby(user, code);

        if (lobby != null) {
            user.sendMessage(new JoinGameResponsePacket(true, lobby.getGame().getSnapshot()));
            lobby.broadcastToUsers(new SnapshotPacket(lobby.getGame().getSnapshot()));
            logger.info(user.getUsername() + " joined lobby " + lobby.getId() + " [Code = " + lobby.getCode() + "]");
        } else {
            user.sendMessage(new JoinGameResponsePacket(false, null));
        }
    }

}