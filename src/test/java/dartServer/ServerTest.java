package dartServer;

import dartServer.networking.artefacts.ContainerEncoder;
import dartServer.networking.artefacts.requests.AuthRequest;
import dartServer.networking.artefacts.requests.CreateGameRequest;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.TimeUnit;

class ServerTest {


    @Test
    public void testAuth() throws Exception {
        String destUri = "ws://127.0.0.1:9000";

        WebSocketClient client = new WebSocketClient();
        SimpleWebSocket socket = new SimpleWebSocket();
        try
        {
            client.start();
            URI uri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, uri, request).get(2,TimeUnit.SECONDS);
            System.err.printf("Connecting to : %s%n", uri);

            socket.send(new CreateGameRequest());

            socket.awaitClose(5, TimeUnit.SECONDS);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            try
            {
                client.stop();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}