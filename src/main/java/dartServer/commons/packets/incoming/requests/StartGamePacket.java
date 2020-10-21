package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;

/**
 * Packet for a owner of a game to request starting it.
 * Note: The StartGamePacket is an empty packet.
 */
public class StartGamePacket implements RequestPacket {

    public StartGamePacket() {
        // StartGamePacket is an empty packet
    }

    @Override
    public String toString() {
        return "StartGame{}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if(getClass() == o.getClass()) return true;
        return false;
    }
}
