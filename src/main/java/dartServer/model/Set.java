package dartServer.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Set {

    private ArrayList<Leg> legs;
    private int legsNeededToWin;
    private int startIndex;

    public Set(int startIndex, int legsNeededToWin) {
        this.legs = new ArrayList<>();
        this.legsNeededToWin = legsNeededToWin;
        this.startIndex = startIndex;
    }

    public int getWinner() {
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

        return -1;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public int getLegsNeededToWin() {
        return legsNeededToWin;
    }

    public int getStartIndex() {
        return startIndex;
    }
}
