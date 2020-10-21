package dartServer.gameengine.model;

import java.util.Objects;

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
