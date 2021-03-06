package dartServer.commons.packets;

import dartServer.commons.packets.incoming.requests.*;
import dartServer.commons.packets.outgoing.broadcasts.GameCanceledPacket;
import dartServer.commons.packets.outgoing.broadcasts.PlayerExitedPacket;
import dartServer.commons.packets.outgoing.broadcasts.PlayerJoinedPacket;
import dartServer.commons.packets.outgoing.broadcasts.SnapshotPacket;
import dartServer.commons.packets.outgoing.unicasts.*;

/**
 * Enum list of all possible payload types
 */
public enum PacketType {

    // Incoming packets
    AUTH_REQUEST("authRequest", AuthRequestPacket.class),
    CREATE_GAME("createGame", CreateGamePacket.class),
    JOIN_GAME("joinGame", JoinGamePacket.class),
    EXIT_GAME("exitGame", ExitGamePacket.class),
    START_GAME("startGame", StartGamePacket.class),
    CANCEL_GAME("cancelGame", CancelGamePacket.class),
    PERFORM_THROW("performThrow", PerformThrowPacket.class),
    UNDO_THROW("undoThrow", UndoThrowPacket.class),
    UPDATE_GAME_CONFIG("updateGameConfig", UpdateGameConfigPacket.class),
    INVITE_TO_GAME("inviteToGame", InviteToGamePacket.class),

    // Outgoing packets
    // unicast
    AUTH_RESPONSE("authResponse", AuthResponsePacket.class),
    CREATE_GAME_RESPONSE("createGameResponse", CreateGameResponsePacket.class),
    START_GAME_RESPONSE("startGameResponse", StartGameResponsePacket.class),
    JOIN_GAME_RESPONSE("joinGameResponse", JoinGameResponsePacket.class),
    INVITE_TO_GAME_RESPONSE("inviteToGameResponse", InviteToGameResponse.class),
    // broadcast
    PLAYER_JOINED("playerJoined", PlayerJoinedPacket.class),
    PLAYER_EXITED("playerExited", PlayerExitedPacket.class),
    GAME_CANCELED("gameCanceled", GameCanceledPacket.class),
    SNAPSHOT("snapshot", SnapshotPacket.class);

    private final Class packetClass;
    private final String jsonTypeName;

    PacketType(String jsonTypeName, Class packetClass) {
        this.jsonTypeName = jsonTypeName;
        this.packetClass = packetClass;
    }

    /**
     * Get the packet type for specified packet class
     *
     * @param packetClass The class of the type
     * @return The packet type
     */
    public static PacketType forClass(Class packetClass) {
        for (PacketType p : values())
            if (p.getPacketClass().equals(packetClass))
                return p;
        return null;
    }

    /**
     * Get the packet type for specified JSON packet type name
     *
     * @param jsonTypeName The json name of the type
     * @return The packet type
     */
    public static PacketType forName(String jsonTypeName) {
        for (PacketType p : values())
            if (p.getJsonType().equals(jsonTypeName))
                return p;
        return null;
    }

    public Class getPacketClass() {
        return packetClass;
    }

    public String getJsonType() {
        return jsonTypeName;
    }

}
