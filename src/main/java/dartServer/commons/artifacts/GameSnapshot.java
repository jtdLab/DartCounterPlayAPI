package dartServer.commons.artifacts;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class GameSnapshot {

    @NotNull
    private final String status;

    @NotNull
    private final String description;

    @NotNull
    private final List<PlayerSnapshot> players;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSnapshot that = (GameSnapshot) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(description, that.description) &&
                Objects.equals(players, that.players);
    }

}
