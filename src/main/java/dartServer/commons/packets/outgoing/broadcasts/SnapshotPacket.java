package dartServer.commons.packets.outgoing.broadcasts;

import dartServer.commons.artifacts.GameSnapshot;
import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;

/**
 * Packet for broadcast to the lobby when the game state changes.
 */
public class SnapshotPacket implements ResponsePacket {

    @NotNull
    private GameSnapshot snapshot;

    public SnapshotPacket(GameSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public GameSnapshot getSnapshot() {
        return snapshot;
    }

    @Override
    public String toString() {
        return "Snapshot{" +
                "snapshot='" + snapshot.toString() +'\'' +
                "}";
    }
}
