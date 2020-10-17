package dartServer.networking.exceptions;


import dartServer.networking.events.ClientExceptionEvent;

public class InvalidJsonException extends ClientException {

    public InvalidJsonException() {
        super(ClientExceptionEvent.ProtocolExceptionReason.INVALID_JSON);
    }
}
