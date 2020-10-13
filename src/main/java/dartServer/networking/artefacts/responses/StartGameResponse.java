package dartServer.networking.artefacts.responses;

import dartServer.model.snapshots.GameSnapshot;
import dartServer.networking.artefacts.Payload;

public class StartGameResponse implements Payload {

    public Boolean successful;
    public GameSnapshot gameSnapshot;

    public StartGameResponse(Boolean successful, GameSnapshot gameSnapshot) {
        this.successful = successful;
        this.gameSnapshot = gameSnapshot;
    }
}
