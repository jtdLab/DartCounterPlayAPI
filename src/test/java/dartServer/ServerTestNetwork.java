
package dartServer;


import helpers.TestClient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServerTestNetwork {
/*
    @Test
    public void testAuth() throws Exception {
        TestClient testClient = new TestClient(9000);
        testClient.connect();
        testClient.send(new AuthRequest("mrjosch", "sanoj050499"));
        testClient.disconnect();
        assertTrue(testClient.getLastReceived() != null);
        assertEquals("authResponse", testClient.getLastReceived().type);
        assertTrue(((AuthResponse) testClient.getLastReceived().packet).successful);
    }

    @Test
    public void testCreateGame() throws Exception {
        TestClient testClient = new TestClient(9000);
        testClient.connect();
        testClient.send(new AuthRequest("mrjosch", "sanoj050499"));
        testClient.send(new CreateGameRequest());
        testClient.disconnect();
        assertTrue(testClient.getLastReceived() != null);
        assertEquals("createGameResponse", testClient.getLastReceived().type);
    }

    @Test
    public void testCancelGame() throws Exception {
        TestClient testClient = new TestClient(9000);
        testClient.connect();
        testClient.send(new AuthRequest("mrjosch", "sanoj050499"));
        testClient.send(new CreateGameRequest());
        testClient.send(new CancelGameRequest());
        testClient.disconnect();
        assertTrue(testClient.getLastReceived() != null);
        assertEquals("authResponse", testClient.getReceived().get(0).type);
        assertEquals("createGameResponse", testClient.getReceived().get(1).type);
        assertEquals("cancelGameResponse", testClient.getReceived().get(2).type);
        assertTrue(((CancelGameResponse) testClient.getLastReceived().packet).successful);
    }

    @Test
    public void testJoinGame() throws Exception {
        TestClient testClient = new TestClient(9000);
        testClient.connect();
        testClient.send(new AuthRequest("mrjosch", "sanoj050499"));
        testClient.send(new CreateGameRequest());
        testClient.disconnect();
        assertEquals("authResponse", testClient.getReceived().get(0).type);
        assertEquals("createGameResponse", testClient.getReceived().get(1).type);

        testClient = new TestClient(9000);
        testClient.connect();
        testClient.send(new AuthRequest("needs00", "sanoj050499"));
        testClient.send(new JoinGameRequest("mrjosch"));
        testClient.disconnect();
        assertEquals("authResponse", testClient.getReceived().get(0).type);
        assertEquals("joinGameResponse", testClient.getReceived().get(1).type);
        assertTrue(((JoinGameResponse) testClient.getLastReceived().packet).successful);
        GameSnapshot gameSnapshot = ((JoinGameResponse) testClient.getLastReceived().packet).gameSnapshot;
        assertTrue(gameSnapshot.players.size() == 2);
    }

    @Test
    public void testLeaveGame() throws Exception {
        TestClient testClient = new TestClient(9000);
        testClient.connect();
        testClient.send(new AuthRequest("mrjosch", "sanoj050499"));
        testClient.send(new CreateGameRequest());
        testClient.disconnect();
        assertEquals("authResponse", testClient.getReceived().get(0).type);
        assertEquals("createGameResponse", testClient.getReceived().get(1).type);

        testClient = new TestClient(9000);
        testClient.connect();
        testClient.send(new AuthRequest("needs00", "sanoj050499"));
        testClient.send(new JoinGameRequest("mrjosch"));
        testClient.send(new LeaveGameRequest());
        testClient.disconnect();
        assertEquals("authResponse", testClient.getReceived().get(0).type);
        assertEquals("joinGameResponse", testClient.getReceived().get(1).type);
        assertEquals("leaveGameResponse", testClient.getReceived().get(2).type);
        assertTrue(((LeaveGameResponse) testClient.getLastReceived().packet).successful);
        // TODO
    }

    @Test
    public void testStartGame1() throws Exception {
        TestClient testClient = new TestClient(9000);
        testClient.connect();
        testClient.send(new AuthRequest("mrjosch", "sanoj050499"));
        testClient.send(new CreateGameRequest());
        testClient.send(new StartGameRequest());
        testClient.disconnect();
        assertEquals("authResponse", testClient.getReceived().get(0).type);
        assertEquals("createGameResponse", testClient.getReceived().get(1).type);
        assertEquals("startGameResponse", testClient.getReceived().get(2).type);
        assertFalse(((StartGameResponse) testClient.getLastReceived().packet).successful);
    }

    @Test
    public void testStartGame2() throws Exception {
        // TODO test successful start
    }

    @Test
    public void testDoThrow() throws Exception {
        // TODO test
    }

    @Test
    public void testUndoThrow() throws Exception {
        // TODO test
    }

*/
}
