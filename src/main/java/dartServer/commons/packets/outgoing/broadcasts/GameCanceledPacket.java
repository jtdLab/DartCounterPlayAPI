package dartServer.commons.packets.outgoing.broadcasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

/**
 * Packet for broadcast to the lobby when the game got canceled.
 * Note: The CreateGamePacket is an empty packet.
 */
public class GameCanceledPacket implements ResponsePacket {

    public GameCanceledPacket() {
        // GameCanceledPacket is an empty packet
    }

    @Override
    public String toString() {
        return "GameCanceled{}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}