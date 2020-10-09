package dartServer.model;

public class Throw {

    private int points;
    private int dartsOnDouble;
    private int dartsThrown;
    private int playerIndex;

    public Throw(int points, int dartsOnDouble, int dartsThrown) {
        this.points = points;
        this.dartsOnDouble = dartsOnDouble;
        this.dartsThrown = dartsThrown;
    }

    public Throw(int points, int dartsOnDouble, int dartsThrown, int playerIndex) {
        this.points = points;
        this.dartsOnDouble = dartsOnDouble;
        this.dartsThrown = dartsThrown;
        this.playerIndex = playerIndex;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDartsOnDouble() {
        return dartsOnDouble;
    }

    public void setDartsOnDouble(int dartsOnDouble) {
        this.dartsOnDouble = dartsOnDouble;
    }

    public int getDartsThrown() {
        return dartsThrown;
    }

    public void setDartsThrown(int dartsThrown) {
        this.dartsThrown = dartsThrown;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
