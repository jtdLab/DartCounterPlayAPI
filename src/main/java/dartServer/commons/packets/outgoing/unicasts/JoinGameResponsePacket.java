package dartServer.commons.packets.outgoing.unicasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Packet for unicast response of the server to a clients joinGame.
 */
public class JoinGameResponsePacket implements ResponsePacket {

    @NotNull
    private final Boolean successful;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameResponsePacket that = (JoinGameResponsePacket) o;
        return Objects.equals(successful, that.successful);
    }

}

