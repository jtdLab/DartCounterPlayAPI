package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;

/**
 * Packet for a owner of a game to request canceling it.
 * Note: The CancelGamePacket is an empty packet.
 */
public class CancelGamePacket implements RequestPacket {

    public CancelGamePacket() {
        // CancelGamePacket is an empty packet
    }

    @Override
    public String toString() {
        return "CancelGame{}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

}
