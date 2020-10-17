package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;

/**
 * Packet for a client to request the create of a new game.
 * Note: The CreateGamePacket is an empty packet.
 */
public class CreateGamePacket implements RequestPacket {

    public CreateGamePacket() {
        // CreateGamePacket is an empty packet
    }

    @Override
    public String toString() {
        return "CreateGame{}";
    }

}