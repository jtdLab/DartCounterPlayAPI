package dartServer.networking.artefacts.responses;

import dartServer.networking.artefacts.Payload;

public class AuthResponse implements Payload {

    public Boolean successful;
    public String details;

    public AuthResponse(Boolean successful, String details) {
        this.successful = successful;
        this.details = details;
    }

    @Override
    public String toString() {
        return "AuthResponse {" + successful + ", " + details + "}";
    }
}
