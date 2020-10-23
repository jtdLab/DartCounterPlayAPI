package dartServer.commons.artifacts;

import com.google.gson.annotations.SerializedName;
import dartServer.gameengine.model.enums.GameStatus;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class GameSnapshot {

    @NotNull
    private final GameStatus status;

    @NotBlank
    private final String description;

    @NotNull
    @Size(min = 2, max = 4)
    private final List<PlayerSnapshot> players;

    public GameSnapshot(GameStatus status, String description, List<PlayerSnapshot> players) {
        this.status = status;
        this.description = description;
        this.players = players;
    }

    public GameStatus getStatus() {
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
