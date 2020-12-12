package dartServer.gameengine.model;

import dartServer.gameengine.model.enums.GameMode;
import dartServer.gameengine.model.enums.GameType;

public class GameConfig {

    private final GameMode mode;
    private final GameType type;
    private final int size;
    private final int startingPoints;

    public GameConfig() {
        mode = GameMode.FIRST_TO;
        type = GameType.LEGS;
        size = 1;
        startingPoints = 501;
    }

    public GameConfig(GameMode mode, GameType type, int size, int startingPoints) {
        this.mode = mode;
        this.type = type;
        this.size = size;
        this.startingPoints = startingPoints;
    }


    public GameMode getMode() {
        return mode;
    }

    public GameType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getStartingPoints() {
        return startingPoints;
    }

    public String getModeAsString() {
        switch (mode) {
            case FIRST_TO:
                return "first to";
            case BEST_OF:
                return "best of";
        }

        return null;
    }

    public String getTypeAsString() {
        switch (type) {
            case LEGS:
                if (size == 1) {
                    return "leg";
                }
                return "legs";
            case SETS:
                if (size == 1) {
                    return "set";
                }
                return "sets";
        }

        return null;
    }
}
