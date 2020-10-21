package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;

/**
 * Packet for a client to request exit out of current game.
 * Note: The ExitGamePacket is an empty packet.
 */
public class ExitGamePacket implements RequestPacket {

    public ExitGamePacket() {
        // CreateGamePacket is an empty packet
    }

    @Override
    public String toString() {
        return "ExitGame{}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}