package dartServer.networking.events;


import dartServer.networking.Client;

/**
 * This event is fired, when the client disconnects from the server.
 */
public class ClientDisconnectEvent extends NetworkEvent {

    public ClientDisconnectEvent(Client client) {
        super(client);
    }
}
