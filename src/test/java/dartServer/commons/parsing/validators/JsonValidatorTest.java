package dartServer.commons.parsing.validators;

import dartServer.commons.artifacts.GameSnapshot;
import dartServer.commons.artifacts.PlayerSnapshot;
import dartServer.commons.packets.PacketContainer;
import dartServer.commons.packets.PacketType;
import dartServer.commons.packets.incoming.requests.*;
import dartServer.commons.packets.outgoing.broadcasts.*;
import dartServer.commons.packets.outgoing.unicasts.AuthResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.CreateGameResponsePacket;
import dartServer.commons.packets.outgoing.unicasts.JoinGameResponsePacket;
import dartServer.gameengine.lobby.Player;
import dartServer.gameengine.model.Throw;
import dartServer.gameengine.model.enums.GameStatus;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonValidatorTest {

    @Test
    public void testIsPacketContainerValid() {
        // valid containerPackets
        // incoming
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new AuthRequestPacket("Mrjosch99", "sanoj050499"))));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new CancelGamePacket())));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new CreateGamePacket())));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new ExitGamePacket())));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new JoinGamePacket(5000))));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new PerformThrowPacket(new Throw(180, 0, 3)))));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new StartGamePacket())));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new UndoThrowPacket())));

        // outgoing
        // broadcasts
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new GameCanceledPacket())));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new GameStartedPacket())));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new PlayerExitedPacket("Mrjosch99"))));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new PlayerJoinedPacket("Mrjosch99"))));
        //assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new SnapshotPacket(new GameSnapshot(GameStatus.PENDING, "first to 3 legs", List.of(new Player("Mrjosch99", null).getSnapshot(), new Player("Needs00", null).getSnapshot()))))));

        // unicasts
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new AuthResponsePacket(true))));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new CreateGameResponsePacket(true))));
        assertTrue(JsonValidator.isPacketContainerValid(new PacketContainer(new JoinGameResponsePacket(true))));

        // invalid containerPackets
        // timestamp null
        assertFalse(JsonValidator.isPacketContainerValid(new PacketContainer(null, PacketType.CREATE_GAME, new CreateGamePacket())));
        // timestamp future
        assertFalse(JsonValidator.isPacketContainerValid(new PacketContainer(new Date(System.currentTimeMillis() + 10000), PacketType.CREATE_GAME, new CreateGamePacket())));
        // payloadType null
        assertFalse(JsonValidator.isPacketContainerValid(new PacketContainer(new Date(), null, new CreateGamePacket())));
        // payloadType not existing
        // TODO
        // payload null
        assertFalse(JsonValidator.isPacketContainerValid(new PacketContainer(new Date(), PacketType.CREATE_GAME, null)));
        // payload invalid
        // TODO
    }

    @Test
    public void testIsPacketValid() {
        // incoming
        testIsAuthRequestPacketValid();
        testIsCancelGamePacketValid();
        testIsCreateGamePacketValid();
        testIsExitGamePacketValid();
        testIsJoinGamePacketValid();
        testIsPerformThrowPacketValid();
        testIsStartGamePacketValid();
        testIsUndoThrowPacketValid();

        // outgoing
        // unicasts
        testIsGameCanceledPacketValid();
        testIsGameStartedPacketValid();
        testIsPlayerExitedPacketValid();
        testIsPlayerJoinedPacketValid();
        testIsPlayerSnapshotPacketValid();

        // broadcasts
        testIsAuthResponsePacketValid();
        testIsCreateGameResponsePacketValid();
        testIsJoinGameResponsePacketValid();
    }

    /**
     * Incoming
     */

    private void testIsAuthRequestPacketValid() {
        // all valid
        assertTrue(JsonValidator.isPacketValid(new AuthRequestPacket("Mrjosch99", "sanoj050499")));
        // username null and password null
        assertFalse(JsonValidator.isPacketValid(new AuthRequestPacket(null, null)));
        // username null and password valid
        assertFalse(JsonValidator.isPacketValid(new AuthRequestPacket(null, "sanoj050499")));
        // username length > 12 and password valid
        assertFalse(JsonValidator.isPacketValid(new AuthRequestPacket("zuLangerUsername", "sanoj050499")));
        // username length < 3 and password valid
        assertFalse(JsonValidator.isPacketValid(new AuthRequestPacket("ha", "sanoj050499")));
        // username containing forbidden chars and password valid
        assertFalse(JsonValidator.isPacketValid(new AuthRequestPacket("*Lars.x*", "sanoj050499")));
        // username valid and password empty
        assertFalse(JsonValidator.isPacketValid(new AuthRequestPacket("mrjosch", "")));
        // username valid and password whitespace only
        assertFalse(JsonValidator.isPacketValid(new AuthRequestPacket("mrjosch", "       ")));
        // username valid and password null
        assertFalse(JsonValidator.isPacketValid(new AuthRequestPacket("mrjosch", null)));
    }

    private void testIsCancelGamePacketValid() {
        assertTrue(JsonValidator.isPacketValid(new CancelGamePacket()));
    }

    private void testIsCreateGamePacketValid() {
        assertTrue(JsonValidator.isPacketValid(new CreateGamePacket()));
    }

    private void testIsExitGamePacketValid() {
        assertTrue(JsonValidator.isPacketValid(new ExitGamePacket()));
    }

    private void testIsJoinGamePacketValid() {
        // code valid
        assertTrue(JsonValidator.isPacketValid(new JoinGamePacket(5000)));
        // code null
        assertFalse(JsonValidator.isPacketValid(new JoinGamePacket(null)));
        // code < 1000
        assertFalse(JsonValidator.isPacketValid(new JoinGamePacket(999)));
        // code > 9999
        assertFalse(JsonValidator.isPacketValid(new JoinGamePacket(10000)));
    }

    private void testIsPerformThrowPacketValid() {
        // throw valid
        assertTrue(JsonValidator.isPacketValid(new PerformThrowPacket(new Throw(180, 0, 3))));
        // throw null
        assertFalse(JsonValidator.isPacketValid(new PerformThrowPacket(null)));
        // Note: The following cases are only a example range of invalid throws for more detailed testing check ThrowValidatorTest.java
        // throw invalid1
        // TODO
        // throw invalid2
        // TODO
        // throw invalid3
        // TODO
    }

    private void testIsStartGamePacketValid() {
        assertTrue(JsonValidator.isPacketValid(new StartGamePacket()));
    }

    private void testIsUndoThrowPacketValid() {
        assertTrue(JsonValidator.isPacketValid(new UndoThrowPacket()));
    }


    /**
     * Outgoing
     */

    /**
     * broadcasts
     */
    private void testIsGameCanceledPacketValid() {
        assertTrue(JsonValidator.isPacketValid(new GameCanceledPacket()));
    }

    private void testIsGameStartedPacketValid() {
        assertTrue(JsonValidator.isPacketValid(new GameStartedPacket()));
    }

    private void testIsPlayerExitedPacketValid() {
        // username valid
        assertTrue(JsonValidator.isPacketValid(new PlayerExitedPacket("Mrjosch99")));
        // username null
        assertFalse(JsonValidator.isPacketValid(new PlayerExitedPacket(null)));
        // username length > 12
        assertFalse(JsonValidator.isPacketValid(new PlayerExitedPacket("zuLangerUsername")));
        // username length < 3
        assertFalse(JsonValidator.isPacketValid(new PlayerExitedPacket("ha")));
        // username containing forbidden chars
        assertFalse(JsonValidator.isPacketValid(new PlayerExitedPacket("*Lars.x*")));
    }

    private void testIsPlayerJoinedPacketValid() {
        // username valid
        assertTrue(JsonValidator.isPacketValid(new PlayerJoinedPacket("Mrjosch99")));
        // username null
        assertFalse(JsonValidator.isPacketValid(new PlayerJoinedPacket(null)));
        // username length > 12
        assertFalse(JsonValidator.isPacketValid(new PlayerJoinedPacket("zuLangerUsername")));
        // username length < 3
        assertFalse(JsonValidator.isPacketValid(new PlayerJoinedPacket("ha")));
        // username containing forbidden chars
        assertFalse(JsonValidator.isPacketValid(new PlayerJoinedPacket("*Lars.x*")));
    }

    private void testIsPlayerSnapshotPacketValid() {
        PlayerSnapshot playerSnapshot1 = new Player("mrjosch", null).getSnapshot();
        PlayerSnapshot playerSnapshot2 = new Player("needs00", null).getSnapshot();

        // all valid
        //assertTrue(JsonValidator.isPacketValid(new SnapshotPacket(new GameSnapshot(GameStatus.PENDING, "first to 3 legs", List.of(playerSnapshot1, playerSnapshot2)))));

        // status null and description valid and players valid
        //assertFalse(JsonValidator.isPacketValid(new SnapshotPacket(new GameSnapshot(null, "first to 3 legs", List.of(playerSnapshot1, playerSnapshot2)))));

        // status valid and description null and players valid
        assertFalse(JsonValidator.isPacketValid(new SnapshotPacket(new GameSnapshot(GameStatus.PENDING, null, List.of(playerSnapshot1, playerSnapshot2)))));

        // status valid and description empty and players valid
        //assertFalse(JsonValidator.isPacketValid(new SnapshotPacket(new GameSnapshot(null, "", List.of(playerSnapshot1, playerSnapshot2)))));

        // status valid and description whitespace only and players valid
        //assertFalse(JsonValidator.isPacketValid(new SnapshotPacket(new GameSnapshot(null, "      ", List.of(playerSnapshot1, playerSnapshot2)))));

        // status valid description valid players size < 2
        //assertFalse(JsonValidator.isPacketValid(new SnapshotPacket(new GameSnapshot(GameStatus.PENDING, "first to 3 legs", List.of(playerSnapshot1)))));

        // status valid description valid players size > 4
        //assertFalse(JsonValidator.isPacketValid(new SnapshotPacket(new GameSnapshot(GameStatus.PENDING, "first to 3 legs", List.of(playerSnapshot1, playerSnapshot2, playerSnapshot1, playerSnapshot2, playerSnapshot1)))));
    }

    /**
     * unicasts
     */
    private void testIsAuthResponsePacketValid() {
        // successful valid
        assertTrue(JsonValidator.isPacketValid(new AuthResponsePacket(true)));
        // successful valid
        assertTrue(JsonValidator.isPacketValid(new AuthResponsePacket(false)));
        // successful null
        assertFalse(JsonValidator.isPacketValid(new AuthResponsePacket(null)));
    }

    private void testIsCreateGameResponsePacketValid() {
        // successful valid
        assertTrue(JsonValidator.isPacketValid(new CreateGameResponsePacket(true)));
        // successful valid
        assertTrue(JsonValidator.isPacketValid(new CreateGameResponsePacket(false)));
        // successful null
        assertFalse(JsonValidator.isPacketValid(new CreateGameResponsePacket(null)));
    }

    private void testIsJoinGameResponsePacketValid() {
        // successful valid
        assertTrue(JsonValidator.isPacketValid(new JoinGameResponsePacket(true)));
        // successful valid
        assertTrue(JsonValidator.isPacketValid(new JoinGameResponsePacket(false)));
        // successful null
        assertFalse(JsonValidator.isPacketValid(new JoinGameResponsePacket(null)));
    }

}