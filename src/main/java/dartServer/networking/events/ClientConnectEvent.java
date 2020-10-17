package dartServer.networking.events;

import dartServer.networking.Client;

/**
 * This event is fired, when a client connects to the server.
 */
public class ClientConnectEvent extends NetworkEvent {

    public ClientConnectEvent(Client client) {
        super(client);
    }
}
