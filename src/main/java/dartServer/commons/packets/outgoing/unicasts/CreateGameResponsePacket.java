package dartServer.commons.packets.outgoing.unicasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;

/**
 * Packet for unicast response of the server to a clients createGame.
 */
public class CreateGameResponsePacket implements ResponsePacket {

    @NotNull
    private final Boolean successful;

    public CreateGameResponsePacket(Boolean successful) {
        this.successful = successful;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    @Override
    public String toString() {
        return "CreateGameResponse{" +
                "successful=" + successful + '\'' +
                "}";
    }
}

