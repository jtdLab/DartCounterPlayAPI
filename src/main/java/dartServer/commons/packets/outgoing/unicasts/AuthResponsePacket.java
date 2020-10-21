package dartServer.commons.packets.outgoing.unicasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Packet for unicast response of the server to a clients authRequest.
 */
public class AuthResponsePacket implements ResponsePacket {

    @NotNull
    private final Boolean successful;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponsePacket that = (AuthResponsePacket) o;
        return Objects.equals(successful, that.successful);
    }

}
