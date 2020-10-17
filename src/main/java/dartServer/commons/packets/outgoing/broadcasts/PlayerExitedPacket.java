package dartServer.commons.packets.outgoing.broadcasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;

/**
 * Packet for broadcast to the lobby when a player exited.
 */
public class PlayerExitedPacket implements ResponsePacket {

    @NotNull
    private String username;

    public PlayerExitedPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "PlayerExited{" +
                "username='" + username +'\'' +
                "}";
    }
}