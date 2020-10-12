package dartServer.networking.artefacts.responses;

import dartServer.model.snapshots.GameSnapshot;
import dartServer.networking.artefacts.Payload;

public class CreateGameResponse implements Payload {

    public GameSnapshot gameSnapshot;

    public CreateGameResponse(GameSnapshot gameSnapshot) {
        this.gameSnapshot = gameSnapshot;
    }
}
