package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Packet for client to join a game.
 */
public class JoinGamePacket implements RequestPacket {

    @NotNull
    @Range(min = 1000, max = 9999)
    private final Integer gameCode;

    public JoinGamePacket(Integer gameCode) {
        this.gameCode = gameCode;
    }

    public Integer getGameCode() {
        return gameCode;
    }

    @Override
    public String toString() {
        return "JoinGame{" +
                "gameCode=" + gameCode + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGamePacket that = (JoinGamePacket) o;
        return Objects.equals(gameCode, that.gameCode);
    }

}
