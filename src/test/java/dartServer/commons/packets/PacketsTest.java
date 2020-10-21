package dartServer.commons.packets;

import dartServer.commons.artifacts.GameSnapshot;
import dartServer.commons.packets.incoming.requests.*;
import dartServer.commons.packets.outgoing.broadcasts.*;
import dartServer.commons.packets.outgoing.unicasts.AuthResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.CreateGameResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.JoinGameResponsePacket;
import dartServer.gameengine.lobby.Player;
import dartServer.gameengine.model.Throw;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacketsTest {

    @Test
    public void testToString() {
        // TODO
    }

    @Test
    public void testEquals() {
        // incoming
        assertEquals(new AuthRequestPacket("mrjosch", "sanoj050499"), new AuthRequestPacket("mrjosch", "sanoj050499"));
        assertEquals(new CancelGamePacket(), new CancelGamePacket());
        assertEquals(new CreateGamePacket(), new CreateGamePacket());
        assertEquals(new ExitGamePacket(), new ExitGamePacket());
        assertEquals(new JoinGamePacket(9999), new JoinGamePacket(9999));
        assertEquals(new PerformThrowPacket(new Throw(180, 0, 3)), new PerformThrowPacket(new Throw(180, 0, 3)));
        assertEquals(new StartGamePacket(), new StartGamePacket());
        assertEquals(new UndoThrowPacket(), new UndoThrowPacket());

        // outgoing
        // broadcasts
        assertEquals(new GameCanceledPacket(), new GameCanceledPacket());
        assertEquals(new GameStartedPacket(), new GameStartedPacket());
        assertEquals(new PlayerExitedPacket("mrjosch"), new PlayerExitedPacket("mrjosch"));
        assertEquals(new PlayerJoinedPacket("mrjosch"), new PlayerJoinedPacket("mrjosch"));
        assertEquals(new SnapshotPacket(new GameSnapshot("running", "first to 3 legs", List.of(new Player("mrjosch", null).getSnapshot(), new Player("needs00", null).getSnapshot()))), new SnapshotPacket(new GameSnapshot("running", "first to 3 legs", List.of(new Player("mrjosch", null).getSnapshot(), new Player("needs00", null).getSnapshot()))));

        // unicasts
        assertEquals(new AuthResponsePacket(true), new AuthResponsePacket(true));
        assertEquals(new CreateGameResponsePacket(true), new CreateGameResponsePacket(true));
        assertEquals(new JoinGameResponsePacket(true), new JoinGameResponsePacket(true));

        // PacketContainer
        assertEquals(new PacketContainer(new CancelGamePacket()), new PacketContainer(new CancelGamePacket()));
    }
}
