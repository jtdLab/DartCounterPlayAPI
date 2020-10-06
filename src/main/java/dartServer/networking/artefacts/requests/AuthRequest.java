package dartServer.networking.artefacts.requests;

import dartServer.networking.artefacts.Payload;

public class AuthRequest implements Payload {

    public String username;
    public String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toString() {
        return "AuthRequest {" + username + ", " + password + "}";
    }
}
