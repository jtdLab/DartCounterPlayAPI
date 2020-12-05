package dartServer.commons.artifacts;

import dartServer.gameengine.model.GameConfig;
import dartServer.gameengine.model.enums.GameStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class GameSnapshot {

    @NotNull
    private final GameStatus status;

    @NotNull
    private final GameConfig config;

    @NotNull
    @Size(min = 1, max = 4)
    private final List<PlayerSnapshot> players;

    public GameSnapshot(GameStatus status, GameConfig config, List<PlayerSnapshot> players) {
        this.status = status;
        this.config = config;
        this.players = players;
    }

    public GameStatus getStatus() {
        return status;
    }

    public GameConfig getConfig() {
        return config;
    }

    public List<PlayerSnapshot> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "GameSnapshot{" +
                "status=" + status +
                ", description='" + config.toString() + '\'' +
                ", players=" + players +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSnapshot that = (GameSnapshot) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(config, that.config) &&
                Objects.equals(players, that.players);
    }

}
