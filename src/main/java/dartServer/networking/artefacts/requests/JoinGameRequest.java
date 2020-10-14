package dartServer.networking.artefacts.requests;

import dartServer.networking.artefacts.Packet;

public class JoinGameRequest implements Packet {

    public String username;

    // TODO specify this class
    public JoinGameRequest(String username) {
        this.username = username;
    }
}
