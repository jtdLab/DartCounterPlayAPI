package dartServer;

import dartServer.networking.artefacts.requests.AuthRequest;
import dartServer.networking.artefacts.requests.CreateGameRequest;
import dartServer.networking.artefacts.responses.AuthResponse;
import helpers.TestClient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    public void testAuth() throws Exception {
        TestClient testClient = new TestClient(9000);
        testClient.connect();
        testClient.send(new AuthRequest("mrjosch", "sanoj050499"));
        testClient.disconnect();
        assertTrue(testClient.getLastReceived() != null);
        assertEquals("authResponse", testClient.getLastReceived().type);
        assertTrue (((AuthResponse) testClient.getLastReceived().payload).successful);
    }

    @Test
    public void testCancelGame() throws Exception {

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
    public void testDoThrow() throws Exception {

    }

    @Test
    public void testJoinGame() throws Exception {

    }

    @Test
    public void testStartGame() throws Exception {

    }

    @Test
    public void testUndoThrow() throws Exception {

    }


}