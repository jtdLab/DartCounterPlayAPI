package dartServer.networking.artefacts.responses;

import dartServer.networking.artefacts.Packet;

public class CancelGameResponse implements Packet {

    public Boolean successful;

    public CancelGameResponse(Boolean successful) {
        this.successful = successful;
    }
}
