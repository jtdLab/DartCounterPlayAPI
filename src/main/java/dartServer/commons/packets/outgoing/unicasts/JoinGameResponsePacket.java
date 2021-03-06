package dartServer.commons.packets.outgoing.unicasts;

import dartServer.commons.artifacts.GameSnapshot;
import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Packet for unicast response of the server to a clients joinGame.
 */
public class JoinGameResponsePacket implements ResponsePacket {

    @NotNull
    private final Boolean successful;

    private final GameSnapshot snapshot;

    public JoinGameResponsePacket(@NotNull Boolean successful, GameSnapshot snapshot) {
        this.successful = successful;
        this.snapshot = snapshot;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public GameSnapshot getSnapshot() {
        return snapshot;
    }

    @Override
    public String toString() {
        return "JoinGameResponsePacket{" +
                "successful=" + successful +
                ", snapshot=" + snapshot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameResponsePacket that = (JoinGameResponsePacket) o;
        return Objects.equals(successful, that.successful) && Objects.equals(snapshot, that.snapshot);
    }

}

