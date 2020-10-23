package dartServer.commons.packets.outgoing.broadcasts;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.commons.validators.Username;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Packet for broadcast to the lobby when a player joins.
 */
public class PlayerJoinedPacket implements ResponsePacket {

    @NotNull
    @Username
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerJoinedPacket that = (PlayerJoinedPacket) o;
        return Objects.equals(username, that.username);
    }

}
