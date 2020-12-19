package dartServer.gameengine.lobby;

import dartServer.commons.artifacts.PlayerSnapshot;
import dartServer.commons.packets.outgoing.unicasts.CreateGameResponsePacket;
import dartServer.gameengine.model.Player;
import dartServer.networking.Client;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
/*
    @Test
    void testSendMessage() {
        EmbeddedChannel channel = new EmbeddedChannel();
        Client client = new Client(channel);
        Player player = new Player("mrjosch", client);

        CreateGameResponsePacket createGameResponsePacket = new CreateGameResponsePacket(true);
        player.sendMessage(createGameResponsePacket);

        assertEquals(createGameResponsePacket, channel.outboundMessages().peek());
    }

    @Test
    void testGetSnapshot() {
        Player player = new Player("mrjosch", null);
        PlayerSnapshot playerSnapshot = player.getSnapshot();
        assertEquals(new PlayerSnapshot(player), playerSnapshot);
    }

    @Test
    void testIsConnected() {
        Player player = new Player("mrjosch", null);
        assertFalse(player.isConnected());
    }

    @Test
    void testGetName() {
        Player player = new Player("mrjosch", null);
        assertEquals("mrjosch", player.getName());
    }

    @Test
    void testIsNext() {
        Player player = new Player("mrjosch", null);
        assertFalse(player.isNext());
    }

    @Test
    void testSetNext() {
        Player player = new Player("mrjosch", null);
        player.setNext(true);
        assertTrue(player.isNext());
    }

    @Test
    void testGetLastThrow() {
        Player player = new Player("mrjosch", null);
        assertEquals(null, player.getLastThrow());
    }

    @Test
    void testSetLastThrow() {
        Player player = new Player("mrjosch", null);
        player.setLastThrow(180);
        assertEquals(180, player.getLastThrow());
    }

    @Test
    void testGetPointsLeft() {
        Player player = new Player("mrjosch", null);
        assertEquals(0, player.getPointsLeft());
    }

    @Test
    void testSetPointsLeft() {
        Player player = new Player("mrjosch", null);
        player.setPointsLeft(501);
        assertEquals(501, player.getPointsLeft());
    }

    @Test
    void testGetDartsThrown() {
        Player player = new Player("mrjosch", null);
        assertEquals(0, player.getDartsThrown());
    }

    @Test
    void testSetDartsThrown() {
        Player player = new Player("mrjosch", null);
        player.setDartsThrown(3);
        assertEquals(3, player.getDartsThrown());
    }

    @Test
    void testGetSets() {
        Player player = new Player("mrjosch", null);
        assertEquals(null, player.getSets());
    }

    @Test
    void testSetSets() {
        Player player = new Player("mrjosch", null);
        player.setSets(3);
        assertEquals(3, player.getSets());
    }

    @Test
    void testGetLegs() {
        Player player = new Player("mrjosch", null);
        assertEquals(0, player.getLegs());
    }

    @Test
    void testSetLegs() {
        Player player = new Player("mrjosch", null);
        player.setLegs(3);
        assertEquals(3, player.getLegs());
    }

    @Test
    void testGetAverage() {
        Player player = new Player("mrjosch", null);
        assertEquals(null, player.getAverage());
    }

    @Test
    void testSetAverage() {
        Player player = new Player("mrjosch", null);
        player.setAverage("0.00");
        assertEquals("0.00", player.getAverage());
    }

    @Test
    void testGetCheckoutPercentage() {
        Player player = new Player("mrjosch", null);
        assertEquals(null, player.getCheckoutPercentage());
    }

    @Test
    void testSetCheckoutPercentage() {
        Player player = new Player("mrjosch", null);
        player.setCheckoutPercentage("0.00");
        assertEquals("0.00", player.getCheckoutPercentage());
    }

    @Test
    void testGetLobbyId() {
        Player player = new Player("mrjosch", null);
        assertEquals(null, player.getLobbyId());
    }

    @Test
    void testSetLobbyId() {
        Player player = new Player("mrjosch", null);
        player.setLobbyId(1000);
        assertEquals(1000, player.getLobbyId());
    }

    @Test
    void testGetClient() {
        Player player = new Player("mrjosch", null);
        assertEquals(null, player.getClient());
    }

    @Test
    void testSetClient() {
        Player player = new Player("mrjosch", null);
        Client client = new Client(null);
        player.setClient(client);
        assertEquals(client, player.getClient());
    }

    @Test
    void testIsPlaying() {
        Player player = new Player("mrjosch", null);
        assertFalse(player.isPlaying());
    }

    @Test
    void testSetPlaying() {
        Player player = new Player("mrjosch", null);
        player.setPlaying(true);
        assertTrue(player.isPlaying());
    }

    @Test
    void testToString() {
        Player player1 = new Player("mrjosch", null);
        assertEquals("User{name='mrjosch', lobbyId=null}", player1.toString());
    }

    @Test
    void testEquals() {
        Player player1 = new Player("mrjosch", null);
        Player player2 = new Player("mrjosch", new Client(null));

        assertEquals(player1, player2);
    }*/
}