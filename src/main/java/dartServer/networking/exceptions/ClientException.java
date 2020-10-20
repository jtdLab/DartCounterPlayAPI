package dartServer.networking.exceptions;

import dartServer.networking.events.ClientExceptionEvent;

public class ClientException extends Exception {

    private final ClientExceptionEvent.ProtocolExceptionReason reason;

    public ClientException(ClientExceptionEvent.ProtocolExceptionReason reason) {
        this.reason = reason;
    }

    public ClientExceptionEvent.ProtocolExceptionReason getReason() {
        return reason;
    }
}
