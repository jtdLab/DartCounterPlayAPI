package dartServer.model.snapshots;

import java.util.List;

public class GameSnapshot {

    public String status;
    public String description;
    public List<PlayerSnapshot> players;

    public GameSnapshot(String status, String description, List<PlayerSnapshot> players) {
        this.status = status;
        this.description = description;
        this.players = players;
    }
}
