package dartServer.commons.packets;

import dartServer.commons.packets.incoming.requests.*;
import dartServer.commons.packets.outgoing.broadcasts.*;
import dartServer.commons.packets.outgoing.unicasts.AuthResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.CreateGameResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.JoinGameResponsePacket;
import dartServer.gameengine.model.Throw;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacketsTest {

    @Test
    public void testToString() {
        // incoming
        assertEquals("AuthRequestPacket{username='mrjosch', password='sanoj050499'}", new AuthRequestPacket("mrjosch", "sanoj050499").toString());
        assertEquals("CancelGame{}", new CancelGamePacket().toString());
        assertEquals("CreateGame{}", new CreateGamePacket().toString());
        assertEquals("ExitGame{}", new ExitGamePacket().toString());
        assertEquals("JoinGamePacket{gameCode=5000}", new JoinGamePacket(5000).toString());
        assertEquals("PerformThrow{throw='Throw{points=180, dartsOnDouble=0, dartsThrown=3, playerIndex=null}'}", new PerformThrowPacket(new Throw(180, 0, 3)).toString());
        assertEquals("StartGame{}", new StartGamePacket().toString());
        assertEquals("UndoThrow{}", new UndoThrowPacket().toString());

        // outgoing
        // broadcasts
        assertEquals("GameCanceled{}", new GameCanceledPacket().toString());
        assertEquals("GameStarted{}", new GameStartedPacket().toString());
        assertEquals("PlayerExited{username='mrjosch'}", new PlayerExitedPacket("mrjosch").toString());
        assertEquals("PlayerJoined{username='mrjosch'}", new PlayerJoinedPacket("mrjosch").toString());
        //assertEquals("Snapshot{snapshot='GameSnapshot{status=RUNNING, description='first to 3 legs', players=[PlayerSnapshot{name='mrjosch', isNext=false, lastThrow=null, pointsLeft=0, dartsThrown=0, sets=null, legs=0, average='null', checkoutPercentage='null'}, PlayerSnapshot{name='needs00', isNext=false, lastThrow=null, pointsLeft=0, dartsThrown=0, sets=null, legs=0, average='null', checkoutPercentage='null'}]}'}",
                //new SnapshotPacket(new GameSnapshot(GameStatus.RUNNING, "first to 3 legs", List.of(new Player("mrjosch", null).getSnapshot(), new Player("needs00", null).getSnapshot()))).toString());
        // unicasts
        assertEquals("AuthResponse{successful=true'}", new AuthResponsePacket(true).toString());
        assertEquals("CreateGameResponse{successful=true'}", new CreateGameResponsePacket(true).toString());
        assertEquals("JoinGameResponse{successful=true'}", new JoinGameResponsePacket(true).toString());
        // PacketContainer
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
        //assertEquals(new SnapshotPacket(new GameSnapshot(GameStatus.RUNNING, "first to 3 legs", List.of(new Player("mrjosch", null).getSnapshot(), new Player("needs00", null).getSnapshot()))), new SnapshotPacket(new GameSnapshot(GameStatus.RUNNING, "first to 3 legs", List.of(new Player("mrjosch", null).getSnapshot(), new Player("needs00", null).getSnapshot()))));

        // unicasts
        assertEquals(new AuthResponsePacket(true), new AuthResponsePacket(true));
        assertEquals(new CreateGameResponsePacket(true), new CreateGameResponsePacket(true));
        assertEquals(new JoinGameResponsePacket(true), new JoinGameResponsePacket(true));

        // PacketContainer
        assertEquals(new PacketContainer(new CancelGamePacket()), new PacketContainer(new CancelGamePacket()));
    }
}
