package dartServer.gameengine.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Leg {

    private final int statingPoints;

    private int[] pointsLeft;
    private int[] dartsThrown;
    private int[] dartsOnDouble;
    private ArrayList<Throw> xThrows;
    private int startIndex;

    private int[] firstNine;

    private int[] fourtyPlus;
    private int[] sixtyPlus;
    private int[] eightyPlus;
    private int[] hundredPlus;
    private int[] hundredTwentyPlus;
    private int[] hundredFourtyPlus;
    private int[] hundredSixtyPlus;
    private int[] hundredEighty;

    public Leg(int startIndex, int numberOfPlayers, int startingPoints) {
        this.statingPoints = startingPoints;
        this.pointsLeft = new int[numberOfPlayers];
        this.dartsThrown = new int[numberOfPlayers];
        this.dartsOnDouble = new int[numberOfPlayers];
        Arrays.fill(pointsLeft, startingPoints);
        Arrays.fill(dartsThrown, 0);
        Arrays.fill(dartsOnDouble, 0);
        this.xThrows = new ArrayList<>();
        this.startIndex = startIndex;

        this.firstNine = new int[numberOfPlayers];

        this.fourtyPlus = new int[numberOfPlayers];
        this.sixtyPlus = new int[numberOfPlayers];
        this.eightyPlus = new int[numberOfPlayers];
        this.hundredPlus = new int[numberOfPlayers];
        this.hundredTwentyPlus = new int[numberOfPlayers];
        this.hundredFourtyPlus = new int[numberOfPlayers];
        this.hundredSixtyPlus = new int[numberOfPlayers];
        this.hundredEighty = new int[numberOfPlayers];

        Arrays.fill(firstNine, 0);

        Arrays.fill(fourtyPlus, 0);
        Arrays.fill(sixtyPlus, 0);
        Arrays.fill(eightyPlus, 0);
        Arrays.fill(hundredPlus, 0);
        Arrays.fill(hundredTwentyPlus, 0);
        Arrays.fill(hundredFourtyPlus, 0);
        Arrays.fill(hundredSixtyPlus, 0);
        Arrays.fill(hundredEighty, 0);
    }

    /**
     * Performs a throw updates the related fields and add the throw to the history
     *
     * @param t
     */
    public void performThrow(Throw t) {
        int index = t.getPlayerIndex();

        pointsLeft[index] -= t.getPoints();
        dartsThrown[index] += t.getDartsThrown();
        dartsOnDouble[index] += t.getDartsOnDouble();

        if(dartsThrown[index] <= 9) {
            firstNine[index] = (statingPoints - pointsLeft[index]) / dartsThrown[index];
        }

        int points = t.getPoints();
        if(points == 180) {
            hundredEighty[index] ++;
        } else if(points >= 160) {
            hundredSixtyPlus[index] ++;
        } else if(points >= 140) {
            hundredFourtyPlus[index] ++;
        } else if(points >= 120) {
            hundredTwentyPlus[index] ++;
        } else if(points >= 100) {
            hundredPlus[index] ++;
        } else if(points >= 80) {
            eightyPlus[index] ++;
        } else if(points >= 60) {
            sixtyPlus[index] ++;
        } else if(points >= 40) {
            fourtyPlus[index] ++;
        }
        xThrows.add(t);
    }

    /**
     * Undos throw restores old fields and removes throw from the history
     *
     * @return undone throw
     */
    public Throw undoThrow() {
        if (!xThrows.isEmpty()) {
            Throw last = xThrows.get(xThrows.size() - 1);

            int index = last.getPlayerIndex();

            pointsLeft[index] += last.getPoints();
            dartsThrown[index] -= last.getDartsThrown();
            dartsOnDouble[index] -= last.getDartsOnDouble();

            if(dartsThrown[index] < 9) {
                firstNine[index] = (statingPoints - pointsLeft[index]) / dartsThrown[index];
            }


            int points = last.getPoints();
            if(points == 180) {
                hundredEighty[index] --;
            } else if(points >= 160) {
                hundredSixtyPlus[index] --;
            } else if(points >= 140) {
                hundredFourtyPlus[index] --;
            } else if(points >= 120) {
                hundredTwentyPlus[index] --;
            } else if(points >= 100) {
                hundredPlus[index] --;
            } else if(points >= 80) {
                eightyPlus[index] --;
            } else if(points >= 60) {
                sixtyPlus[index] --;
            } else if(points >= 40) {
                fourtyPlus[index] --;
            }

            xThrows.remove(xThrows.size() - 1);

            return last;
        }
        return null;
    }

    /**
     * @return index of winner or -1
     */
    public Integer getWinner() {
        for (int i = 0; i < pointsLeft.length; i++) {
            if (pointsLeft[i] == 0) {
                return i;
            }
        }

        return null;
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

    public int[] getFirstNine() {
        return firstNine;
    }

    public void setFirstNine(int[] firstNine) {
        this.firstNine = firstNine;
    }

    public int[] getFourtyPlus() {
        return fourtyPlus;
    }

    public void setFourtyPlus(int[] fourtyPlus) {
        this.fourtyPlus = fourtyPlus;
    }

    public int[] getSixtyPlus() {
        return sixtyPlus;
    }

    public void setSixtyPlus(int[] sixtyPlus) {
        this.sixtyPlus = sixtyPlus;
    }

    public int[] getEightyPlus() {
        return eightyPlus;
    }

    public void setEightyPlus(int[] eightyPlus) {
        this.eightyPlus = eightyPlus;
    }

    public int[] getHundredPlus() {
        return hundredPlus;
    }

    public void setHundredPlus(int[] hundredPlus) {
        this.hundredPlus = hundredPlus;
    }

    public int[] getHundredTwentyPlus() {
        return hundredTwentyPlus;
    }

    public void setHundredTwentyPlus(int[] hundredTwentyPlus) {
        this.hundredTwentyPlus = hundredTwentyPlus;
    }

    public int[] getHundredFourtyPlus() {
        return hundredFourtyPlus;
    }

    public void setHundredFourtyPlus(int[] hundredFourtyPlus) {
        this.hundredFourtyPlus = hundredFourtyPlus;
    }

    public int[] getHundredSixtyPlus() {
        return hundredSixtyPlus;
    }

    public void setHundredSixtyPlus(int[] hundredSixtyPlus) {
        this.hundredSixtyPlus = hundredSixtyPlus;
    }

    public int[] getHundredEighty() {
        return hundredEighty;
    }

    public void setHundredEighty(int[] hundredEighty) {
        this.hundredEighty = hundredEighty;
    }
}
