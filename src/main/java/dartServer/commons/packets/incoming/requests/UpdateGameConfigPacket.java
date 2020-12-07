package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;
import dartServer.gameengine.model.GameConfig;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UpdateGameConfigPacket implements RequestPacket {

    @NotNull
    private final GameConfig gameConfig;

    public UpdateGameConfigPacket(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    @Override
    public String toString() {
        return "UpdateGameConfigPacket{" +
                "gameConfig=" + gameConfig +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateGameConfigPacket that = (UpdateGameConfigPacket) o;
        return Objects.equals(gameConfig, that.gameConfig);
    }

}
