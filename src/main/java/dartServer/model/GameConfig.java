package dartServer.model;

public class GameConfig {

    private GameMode mode;
    private GameType type;
    private int size;
    private int startingPoints;

    public GameConfig() {

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
                return "FIRST TO";
            case BEST_OF:
                return "BEST OF";
        }

        return null;
    }

    public String getTypeAsString() {
        switch (type) {
            case LEGS:
                if(size == 1) {
                    return "LEG";
                }
                return "LEGS";
            case SETS:
                if(size == 1) {
                    return "SET";
                }
                return "SETS";
        }

        return null;
    }
}
