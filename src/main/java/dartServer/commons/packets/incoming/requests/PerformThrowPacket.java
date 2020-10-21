package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;
import dartServer.gameengine.model.Throw;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Packet for a client to request performing a throw.
 */
public class PerformThrowPacket implements RequestPacket {

    // TODO add throw validation annotation

    @NotNull
    private final Throw t;

    public PerformThrowPacket(Throw t) {
        this.t = t;
    }

    public Throw getThrow() {
        return t;
    }

    @Override
    public String toString() {
        return "PerformThrow{" +
                "throw='" + t.toString() + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerformThrowPacket that = (PerformThrowPacket) o;
        return Objects.equals(t, that.t);
    }

}
