package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;

/**
 * Packet for a client to request undo of his last throw.
 * Note: The UndoThrowPacket is an empty packet.
 */
public class UndoThrowPacket implements RequestPacket {

    public UndoThrowPacket() {
        // UndoThrowPacket is an empty packet
    }

    @Override
    public String toString() {
        return "UndoThrow{}";
    }

}