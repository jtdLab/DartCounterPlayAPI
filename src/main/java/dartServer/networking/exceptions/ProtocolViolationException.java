package dartServer.networking.exceptions;


import dartServer.networking.events.ClientExceptionEvent;

public class ProtocolViolationException extends ClientException {

    public ProtocolViolationException() {
        super(ClientExceptionEvent.ProtocolExceptionReason.PROTOCOL_VIOLATION);
    }
}
