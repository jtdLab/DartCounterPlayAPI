package dartServer.gameengine.model;

public class Throw {

    private int points;
    private int dartsOnDouble;
    private int dartsThrown;
    private Integer playerIndex;

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

    public Integer getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    @Override
    public String toString() {
        return "Throw{" +
                "points=" + points +
                ", dartsOnDouble=" + dartsOnDouble +
                ", dartsThrown=" + dartsThrown +
                ", playerIndex=" + playerIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Throw aThrow = (Throw) o;
        return points == aThrow.points &&
                dartsOnDouble == aThrow.dartsOnDouble &&
                dartsThrown == aThrow.dartsThrown &&
                playerIndex == aThrow.playerIndex;
    }

}
