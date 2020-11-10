package dartServer.commons.packets.outgoing.broadcasts;

import dartServer.commons.packets.outgoing.ResponsePacket;

/**
 * Packet for broadcast to the lobby when the game starts.
 * Note: The CreateGamePacket is an empty packet.
 */
public class GameStartedPacket implements ResponsePacket {

    public GameStartedPacket() {
        // GameStartedPacket is an empty packet
    }

    @Override
    public String toString() {
        return "GameStarted{}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}