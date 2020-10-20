package dartServer.commons.packets.outgoing.broadcasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;

/**
 * Packet for broadcast to the lobby when a player joins.
 */
public class PlayerJoinedPacket implements ResponsePacket {

    @NotNull
    private final String username;

    public PlayerJoinedPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "PlayerJoined{" +
                "username='" + username + '\'' +
                "}";
    }
}
