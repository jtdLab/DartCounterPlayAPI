package dartServer.commons.packets.outgoing.unicasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;

/**
 * Packet for unicast response of the server to a clients joinGame.
 */
public class JoinGameResponsePacket implements ResponsePacket {

    @NotNull
    private Boolean successful;

    public JoinGameResponsePacket(Boolean successful) {
        this.successful = successful;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    @Override
    public String toString() {
        return "JoinGameResponse{" +
                "successful=" + successful + '\'' +
                "}";
    }
}

