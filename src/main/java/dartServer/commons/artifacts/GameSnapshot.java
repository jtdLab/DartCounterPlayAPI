package dartServer.commons.artifacts;

import javax.validation.constraints.NotNull;
import java.util.List;

public class GameSnapshot {

    @NotNull
    private String status;

    @NotNull
    private String description;

    @NotNull
    private List<PlayerSnapshot> players;

    public GameSnapshot(String status, String description, List<PlayerSnapshot> players) {
        this.status = status;
        this.description = description;
        this.players = players;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public List<PlayerSnapshot> getPlayers() {
        return players;
    }
}
