package dartServer.commons.artifacts;

import dartServer.commons.validators.Username;
import dartServer.gameengine.lobby.Player;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class PlayerSnapshot {

    @Username
    private final String name;

    @NotNull
    private final Boolean isNext;

    private final Integer lastThrow;

    @PositiveOrZero
    private final int pointsLeft;

    @PositiveOrZero
    private final int dartsThrown;

    private final Integer sets;

    @PositiveOrZero
    private final int legs;

    @NotNull
    private final String average;

    @NotNull
    private final String checkoutPercentage;

    @PositiveOrZero
    private final int firstNine;

    @PositiveOrZero
    private final int fourtyPlus;

    @PositiveOrZero
    private final int sixtyPlus;

    @PositiveOrZero
    private final int eightyPlus;

    @PositiveOrZero
    private final int hundredPlus;

    @PositiveOrZero
    private final int hundredTwentyPlus;

    @PositiveOrZero
    private final int hundredFourtyPlus;

    @PositiveOrZero
    private final int hundredSixtyPlus;

    @PositiveOrZero
    private final int hundredEighty;


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
        firstNine = player.getFirstNine();
        fourtyPlus = player.getFourtyPlus();
        sixtyPlus = player.getSixtyPlus();
        eightyPlus = player.getEightyPlus();
        hundredPlus = player.getHundredPlus();
        hundredTwentyPlus = player.getHundredTwentyPlus();
        hundredFourtyPlus = player.getHundredFourtyPlus();
        hundredSixtyPlus = player.getHundredSixtyPlus();
        hundredEighty = player.getHundredEighty();
    }

    public String getName() {
        return name;
    }

    public Boolean isNext() {
        return isNext;
    }

    public Integer getLastThrow() {
        return lastThrow;
    }

    public int getPointsLeft() {
        return pointsLeft;
    }

    public int getDartsThrown() {
        return dartsThrown;
    }

    public Integer getSets() {
        return sets;
    }

    public int getLegs() {
        return legs;
    }

    public String getAverage() {
        return average;
    }

    public String getCheckoutPercentage() {
        return checkoutPercentage;
    }

    @Override
    public String toString() {
        return "PlayerSnapshot{" +
                "name='" + name + '\'' +
                ", isNext=" + isNext +
                ", lastThrow=" + lastThrow +
                ", pointsLeft=" + pointsLeft +
                ", dartsThrown=" + dartsThrown +
                ", sets=" + sets +
                ", legs=" + legs +
                ", average='" + average + '\'' +
                ", checkoutPercentage='" + checkoutPercentage + '\'' +
                ", firstNine=" + firstNine +
                ", fourtyPlus=" + fourtyPlus +
                ", sixtyPlus=" + sixtyPlus +
                ", eightyPlus=" + eightyPlus +
                ", hundredPlus=" + hundredPlus +
                ", hundredTwentyPlus=" + hundredTwentyPlus +
                ", hundredFourtyPlus=" + hundredFourtyPlus +
                ", hundredSixtyPlus=" + hundredSixtyPlus +
                ", hundredEighty=" + hundredEighty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerSnapshot that = (PlayerSnapshot) o;
        return lastThrow == that.lastThrow &&
                pointsLeft == that.pointsLeft &&
                dartsThrown == that.dartsThrown &&
                sets == that.sets &&
                legs == that.legs &&
                Objects.equals(name, that.name) &&
                Objects.equals(isNext, that.isNext) &&
                Objects.equals(average, that.average) &&
                Objects.equals(checkoutPercentage, that.checkoutPercentage);
    }

}
