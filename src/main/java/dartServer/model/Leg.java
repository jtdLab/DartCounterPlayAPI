package dartServer.model;

import java.util.*;

public class Leg {

    private int[] pointsLeft;
    private int[] dartsThrown;
    private int[] dartsOnDouble;
    private ArrayList<Throw> xThrows;
    private int startIndex;

    Leg(int startIndex, int numberOfPlayers, int startingPoints) {
        this.pointsLeft = new int[numberOfPlayers];
        this.dartsThrown = new int[numberOfPlayers];
        this.dartsOnDouble = new int[numberOfPlayers];
        Arrays.fill(pointsLeft, startingPoints);
        Arrays.fill(dartsThrown, 0);
        Arrays.fill(dartsOnDouble, 0);
        this.xThrows = new ArrayList<>();
        this.startIndex = startIndex;
    }

    public void performThrow(Throw t) {
        pointsLeft[t.getPlayerIndex()] -= t.getPoints();
        dartsThrown[t.getPlayerIndex()] += t.getDartsThrown();
        dartsOnDouble[t.getPlayerIndex()] += t.getDartsOnDouble();
        xThrows.add(t);
    }

    Throw undoThrow() {
        Throw last = xThrows.get(xThrows.size()-1);
        pointsLeft[last.getPlayerIndex()] += last.getPoints();
        dartsThrown[last.getPlayerIndex()] -= last.getDartsThrown();
        dartsOnDouble[last.getPlayerIndex()] -= last.getDartsOnDouble();
        xThrows.remove(xThrows.size()-1);

        return last;
    }

    /**
     * @return index of winner or -1
     */
    public int getWinner() {
        for (int i = 0; i < pointsLeft.length ; i++) {
            if(pointsLeft[i] == 0) {
                return i;
            }
        }

        return -1;
    }

    public int[] getPointsLeft() {
        return pointsLeft;
    }

    public void setPointsLeft(int[] pointsLeft) {
        this.pointsLeft = pointsLeft;
    }

    public int[] getDartsThrown() {
        return dartsThrown;
    }

    public void setDartsThrown(int[] dartsThrown) {
        this.dartsThrown = dartsThrown;
    }

    public int[] getDartsOnDouble() {
        return dartsOnDouble;
    }

    public void setDartsOnDouble(int[] dartsOnDouble) {
        this.dartsOnDouble = dartsOnDouble;
    }

    public ArrayList<Throw> getThrows() {
        return xThrows;
    }

    public void setThrows(ArrayList<Throw> xThrows) {
        this.xThrows = xThrows;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
