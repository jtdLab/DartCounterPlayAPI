package dartServer.gameengine.model;

public class Throw {

    private int points;
    private int dartsOnDouble;
    private int dartsThrown;
    private int userIndex;

    public Throw(int points, int dartsOnDouble, int dartsThrown, int playerIndex) {
        this.points = points;
        this.dartsOnDouble = dartsOnDouble;
        this.dartsThrown = dartsThrown;
        this.userIndex = playerIndex;
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

    public int getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(int userIndex) {
        this.userIndex = userIndex;
    }
}
