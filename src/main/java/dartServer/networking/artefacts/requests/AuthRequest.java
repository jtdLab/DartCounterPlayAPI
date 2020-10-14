package dartServer.networking.artefacts.requests;

import dartServer.networking.artefacts.Packet;

public class AuthRequest implements Packet {

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
