package dartServer.commons.packets.outgoing.unicasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;

/**
 * Packet for unicast response of the server to a clients authRequest.
 */
public class AuthResponsePacket implements ResponsePacket {

    @NotNull
    private Boolean successful;

    public AuthResponsePacket(Boolean successful) {
        this.successful = successful;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "successful=" + successful + '\'' +
                "}";
    }
}
