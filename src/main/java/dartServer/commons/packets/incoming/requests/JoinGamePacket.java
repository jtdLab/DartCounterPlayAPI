package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;

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

}
