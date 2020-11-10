package dartServer.gameengine.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Set {

    private final ArrayList<Leg> legs;
    private final int legsNeededToWin;
    private final int startIndex;

    public Set(int startIndex, int legsNeededToWin) {
        this.legs = new ArrayList<>();
        this.legsNeededToWin = legsNeededToWin;
        this.startIndex = startIndex;
    }

    public Integer getWinner() {
        ArrayList<Integer> winners = new ArrayList<>();
        for (Leg leg : legs) {
            winners.add(leg.getWinner());
        }

        HashMap<Integer, Integer> map = new HashMap();
        winners.forEach((element) -> {
            if (!map.containsKey(element)) {
                map.put(element, 1);
            } else {
                map.put(element, (map.get(element) + 1));
            }
        });

        for (Integer key : map.keySet()) {
            if (map.get(key) == legsNeededToWin) {
                return key;
            }
        }

        return null;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void addLeg(Leg leg) {
        legs.add(leg);
    }

    public int getLegsNeededToWin() {
        return legsNeededToWin;
    }

    public int getStartIndex() {
        return startIndex;
    }
}
