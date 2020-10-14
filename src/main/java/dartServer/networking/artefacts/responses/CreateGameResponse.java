package dartServer.networking.artefacts.responses;

import dartServer.model.snapshots.GameSnapshot;
import dartServer.networking.artefacts.Packet;

public class CreateGameResponse implements Packet {

    public GameSnapshot gameSnapshot;

    public CreateGameResponse(GameSnapshot gameSnapshot) {
        this.gameSnapshot = gameSnapshot;
    }
}
