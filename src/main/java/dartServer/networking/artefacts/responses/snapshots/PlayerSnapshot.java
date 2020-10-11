package dartServer.networking.artefacts.responses.snapshots;

import dartServer.model.Player;

public class PlayerSnapshot {

    public String name;
    public boolean isNext;

    public int lastThrow;
    public int pointsLeft;
    public int dartsThrown;

    public int sets;
    public int legs;

    public String average;
    public String checkoutPercentage;

    public PlayerSnapshot(Player player) {
        name = player.getName();
        isNext = player.isNext();
        lastThrow = player.getLastThrow();
        pointsLeft = player.getPointsLeft();
        dartsThrown = player.getDartsThrown();
        sets = player.getSets();
        legs = player.getLegs();
        average = player.getAverage();
        checkoutPercentage = player.getCheckoutPercentage();
    }
}
