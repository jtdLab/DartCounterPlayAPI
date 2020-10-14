package dartServer.networking.artefacts.responses;

import dartServer.networking.artefacts.Packet;

public class LeaveGameResponse implements Packet {

    public Boolean successful;

    public LeaveGameResponse(Boolean successful) {
        this.successful = successful;
    }
}
