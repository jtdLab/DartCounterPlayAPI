package dartServer.model;

import java.util.List;

public class ThrowValidator {

    public static boolean isValidThrow(Throw t, int pointsLeft) {
        if (t == null) return false;

        if (t.getPoints() < 0 || t.getPoints() > 180) return false;
        if (t.getPoints() > pointsLeft) return false;
        if (List.of(163, 166, 169, 172, 173, 175, 176, 178, 179).contains(t.getPoints())) return false;

        if (t.getDartsThrown() < 0 || t.getDartsThrown() > 3) return false;

        if (t.getDartsOnDouble() < 0 || t.getDartsOnDouble() > 3) return false;
        if (t.getDartsOnDouble() > t.getDartsThrown()) return false;
        if (t.getDartsOnDouble() > 0 && !isValidFinish(pointsLeft)) return false;
        if (t.getDartsOnDouble() == 2 && (isThreeDartFinish(pointsLeft) && !isOneDartFinish(pointsLeft) && !isTwoDartFinish(pointsLeft)))
            return false;
        if (t.getDartsOnDouble() == 3 && !isOneDartFinish(pointsLeft)) return false;

        return true;
    }

    public static boolean isValidFinish(int points) {
        return isThreeDartFinish(points) || isTwoDartFinish(points) || isOneDartFinish(points);
    }

    /**
     * @param points
     * @return true if its possible to finish points with 3 darts false else
     */
    private static boolean isThreeDartFinish(int points) {
        if (points < 2 || points > 170) return false;
        if (List.of(159, 162, 163, 165, 166, 168, 169).contains(points)) return false;

        return true;
    }

    /**
     * @param points
     * @return true if its possible to finish points with 2 darts false else
     */
    private static boolean isTwoDartFinish(int points) {
        if (points < 2 || points > 110) return false;
        if (List.of(99, 102, 103, 105, 106, 108, 109).contains(points)) return false;

        return true;
    }

    /**
     * @param points
     * @return true if its possible to finish points with 1 darts false else
     */
    private static boolean isOneDartFinish(int points) {
        if (points < 2 || points > 50) return false;
        return points == 50 || (points <= 40 && points % 2 == 0);
    }
}
