package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;

import java.util.Objects;

/**
 * Packet for client to join a game.
 */
public class JoinGamePacket implements RequestPacket {

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
