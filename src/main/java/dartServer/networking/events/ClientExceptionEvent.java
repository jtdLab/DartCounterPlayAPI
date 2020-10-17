package dartServer.networking.events;


import dartServer.networking.Client;

/**
 * This event is fired, when the client sends an invalid packet or anything other than JSON.
 * The client will be kicked after this event is called.
 */
public class ClientExceptionEvent extends NetworkEvent {

    ProtocolExceptionReason reason;

    public ClientExceptionEvent(Client client) {
        this(client, ProtocolExceptionReason.UNKNOWN);
    }

    public ClientExceptionEvent(Client client, ProtocolExceptionReason reason) {
        super(client);
        this.reason = reason;
    }

    public enum ProtocolExceptionReason {
        UNKNOWN, INVALID_JSON, PROTOCOL_VIOLATION
    }
}
