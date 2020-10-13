package dartServer.networking.artefacts.responses;

import dartServer.model.snapshots.GameSnapshot;
import dartServer.networking.artefacts.Payload;

public class JoinGameResponse implements Payload {

    public Boolean successful;
    public GameSnapshot gameSnapshot;

    public JoinGameResponse(Boolean successful, GameSnapshot gameSnapshot) {
        this.successful = successful;
        this.gameSnapshot = gameSnapshot;
    }
}
