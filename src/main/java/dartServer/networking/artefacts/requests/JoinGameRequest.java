package dartServer.networking.artefacts.requests;

import dartServer.networking.artefacts.Payload;

public class JoinGameRequest implements Payload {

    public String username;

    // TODO specify this class
    public JoinGameRequest(String username) {
        this.username = username;
    }
}
