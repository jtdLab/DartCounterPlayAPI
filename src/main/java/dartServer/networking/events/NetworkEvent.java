package dartServer.networking.events;


import dartServer.networking.Client;

/**
 * A general NetworkEvent that contains the client that caused the event.
 */
public abstract class NetworkEvent {

    /**
     * The client that caused the event
     */
    private Client client;

    public NetworkEvent(Client client) {
        this.client = client;
    }

    /**
     * Get the client that caused the event
     *
     * @return Client
     */
    public Client getClient() {
        return client;
    }

}
