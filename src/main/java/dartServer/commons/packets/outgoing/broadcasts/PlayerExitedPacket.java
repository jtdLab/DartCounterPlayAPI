package dartServer.commons.packets.outgoing.broadcasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Packet for broadcast to the lobby when a player exited.
 */
public class PlayerExitedPacket implements ResponsePacket {

    @NotNull
    private final String username;

    public PlayerExitedPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "PlayerExited{" +
                "username='" + username + '\'' +
                "}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerExitedPacket that = (PlayerExitedPacket) o;
        return Objects.equals(username, that.username);
    }

}