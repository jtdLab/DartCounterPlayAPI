package dartServer.tests;

import dartServer.commons.packets.incoming.requests.AuthRequestPacket;
import dartServer.commons.packets.incoming.requests.CreateGamePacket;
import dartServer.commons.packets.incoming.requests.JoinGamePacket;
import helpers.TestClient;
import org.junit.jupiter.api.Test;

public class Tests {

    @Test
    public void test1() throws Exception {
        TestClient client1 = new TestClient(9000);
        client1.connect();
        client1.send(new AuthRequestPacket("mrjosch", "sanoj050499"));
        client1.send(new CreateGamePacket());

        TestClient client2 = new TestClient(9000);
        client2.connect();
        client2.send(new AuthRequestPacket("needs00", "sanoj050499"));
        client2.send(new JoinGamePacket(1000));
    }
}
