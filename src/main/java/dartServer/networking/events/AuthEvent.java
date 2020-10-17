package dartServer.networking.events;

import dartServer.networking.Client;

/**
 * This event is fired, when a client request authentication.
 */
public class AuthEvent extends NetworkEvent {

    public AuthEvent(Client client) {
        super(client);
    }
}
