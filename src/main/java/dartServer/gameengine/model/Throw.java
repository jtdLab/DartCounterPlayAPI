package dartServer.gameengine.model;

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

    public int getPoints() {
        return points;
    }

    public int getDartsOnDouble() {
        return dartsOnDouble;
    }

    public int getDartsThrown() {
        return dartsThrown;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
