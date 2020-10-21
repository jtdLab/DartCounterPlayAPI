package dartServer.commons.packets;

import dartServer.commons.packets.incoming.requests.*;
import dartServer.commons.packets.outgoing.broadcasts.*;
import dartServer.commons.packets.outgoing.unicasts.AuthResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.CreateGameResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.JoinGameResponsePacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketTypeTest {

    @Test
    public void testForClass() {
        // Incoming packets
        assertEquals(PacketType.AUTH_REQUEST, PacketType.forClass(AuthRequestPacket.class));
        assertEquals(PacketType.CREATE_GAME, PacketType.forClass(CreateGamePacket.class));
        assertEquals(PacketType.JOIN_GAME, PacketType.forClass(JoinGamePacket.class));
        assertEquals(PacketType.EXIT_GAME, PacketType.forClass(ExitGamePacket.class));
        assertEquals(PacketType.START_GAME, PacketType.forClass(StartGamePacket.class));
        assertEquals(PacketType.CANCEL_GAME, PacketType.forClass(CancelGamePacket.class));
        assertEquals(PacketType.PERFORM_THROW, PacketType.forClass(PerformThrowPacket.class));
        assertEquals(PacketType.UNDO_THROW, PacketType.forClass(UndoThrowPacket.class));

        // Outgoing packets
        // unicast
        assertEquals(PacketType.AUTH_RESPONSE, PacketType.forClass(AuthResponsePacket.class));
        assertEquals(PacketType.CREATE_GAME_RESPONSE, PacketType.forClass(CreateGameResponsePacket.class));
        assertEquals(PacketType.JOIN_GAME_RESPONSE, PacketType.forClass(JoinGameResponsePacket.class));
        // broadcast
        assertEquals(PacketType.PLAYER_JOINED, PacketType.forClass(PlayerJoinedPacket.class));
        assertEquals(PacketType.PLAYER_EXITED, PacketType.forClass(PlayerExitedPacket.class));
        assertEquals(PacketType.GAME_STARTED, PacketType.forClass(GameStartedPacket.class));
        assertEquals(PacketType.GAME_CANCELED, PacketType.forClass(GameCanceledPacket.class));
        assertEquals(PacketType.SNAPSHOT, PacketType.forClass(SnapshotPacket.class));
    }

    @Test
    public void testForName() {
        // Incoming packets
        assertEquals(PacketType.AUTH_REQUEST, PacketType.forName("authRequest"));
        assertEquals(PacketType.CREATE_GAME, PacketType.forName("createGame"));
        assertEquals(PacketType.JOIN_GAME, PacketType.forName("joinGame"));
        assertEquals(PacketType.EXIT_GAME, PacketType.forName("exitGame"));
        assertEquals(PacketType.START_GAME, PacketType.forName("startGame"));
        assertEquals(PacketType.CANCEL_GAME, PacketType.forName("cancelGame"));
        assertEquals(PacketType.PERFORM_THROW, PacketType.forName("performThrow"));
        assertEquals(PacketType.UNDO_THROW, PacketType.forName("undoThrow"));

        // Outgoing packets
        // unicast
        assertEquals(PacketType.AUTH_RESPONSE, PacketType.forName("authResponse"));
        assertEquals(PacketType.CREATE_GAME_RESPONSE, PacketType.forName("createGameResponse"));
        assertEquals(PacketType.JOIN_GAME_RESPONSE, PacketType.forName("joinGameResponse"));
        // broadcast
        assertEquals(PacketType.PLAYER_JOINED, PacketType.forName("playerJoined"));
        assertEquals(PacketType.PLAYER_EXITED, PacketType.forName("playerExited"));
        assertEquals(PacketType.GAME_STARTED, PacketType.forName("gameStarted"));
        assertEquals(PacketType.GAME_CANCELED, PacketType.forName("gameCanceled"));
        assertEquals(PacketType.SNAPSHOT, PacketType.forName("snapshot"));
    }

}