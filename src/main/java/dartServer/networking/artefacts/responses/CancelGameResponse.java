package dartServer.networking.artefacts.responses;

import dartServer.networking.artefacts.Payload;

public class CancelGameResponse implements Payload {

    public Boolean successful;

    public CancelGameResponse(Boolean successful) {
        this.successful = successful;
    }
}
