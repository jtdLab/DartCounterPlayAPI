package dartServer.networking.artefacts.responses;

import dartServer.networking.artefacts.Payload;

public class LeaveGameResponse implements Payload {

    public Boolean successful;

    public LeaveGameResponse(Boolean successful) {
        this.successful = successful;
    }
}
