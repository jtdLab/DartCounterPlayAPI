package dartServer.networking.artefacts.responses;

import dartServer.networking.artefacts.Payload;

import java.util.List;

public class GameSnapshot implements Payload {

    public String status;
    public String description;
    public List<PlayerSnapshot> players;

    public GameSnapshot(String status, String description, List<PlayerSnapshot> players) {
        this.status = status;
        this.description = description;
        this.players = players;
    }
}
