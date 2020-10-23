package dartServer.commons.packets.outgoing.broadcasts;

import dartServer.commons.artifacts.GameSnapshot;
import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Packet for broadcast to the lobby when the game state changes.
 */
public class SnapshotPacket implements ResponsePacket {

    @NotNull
    @Valid
    private final GameSnapshot snapshot;

    public SnapshotPacket(GameSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public GameSnapshot getSnapshot() {
        return snapshot;
    }

    @Override
    public String toString() {
        return "Snapshot{" +
                "snapshot='" + snapshot.toString() + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnapshotPacket that = (SnapshotPacket) o;
        return Objects.equals(snapshot, that.snapshot);
    }

}
