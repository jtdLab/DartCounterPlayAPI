package dartServer.networking.artefacts.responses;

import dartServer.model.snapshots.GameSnapshot;
import dartServer.networking.artefacts.Packet;

public class StartGameResponse implements Packet {

    public Boolean successful;
    public GameSnapshot gameSnapshot;

    public StartGameResponse(Boolean successful, GameSnapshot gameSnapshot) {
        this.successful = successful;
        this.gameSnapshot = gameSnapshot;
    }
}
