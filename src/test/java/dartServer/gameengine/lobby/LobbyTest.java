package dartServer.gameengine.lobby;

import dartServer.gameengine.model.Player;
import dartServer.networking.Client;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

class LobbyTest {

    @Test
    void testAddPlayer() {
        EmbeddedChannel channel = new EmbeddedChannel();
        Client client = new Client(channel);
        Player player = new Player("mrjosch", client);
        Lobby lobby = new Lobby(player);

        lobby.addPlayer(new Player("", null));
    }

    @Test
    void testRemovePlayer() {
    }

    @Test
    void testStartGame() {
    }

    @Test
    void testPerformThrow() {
    }

    @Test
    void testUndoThrow() {
    }

    @Test
    void testBroadcastToPlayers() {
    }

    @Test
    void testGetId() {
    }

    @Test
    void testGetGame() {
    }

    @Test
    void testGetPlayers() {
    }

    @Test
    void testGetCode() {
    }

    @Test
    void testToString() {
    }
}