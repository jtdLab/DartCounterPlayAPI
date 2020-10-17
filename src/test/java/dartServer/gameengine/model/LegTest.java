package dartServer.gameengine.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LegTest {

    @Test
    void init() {
        Leg leg = new Leg(0, 2, 501);
        assertArrayEquals(new int[]{501, 501}, leg.getPointsLeft());
        assertArrayEquals(new int[]{0, 0}, leg.getDartsThrown());
        assertArrayEquals(new int[]{0, 0}, leg.getDartsOnDouble());
        assertEquals(0, leg.getThrows().size());
        assertEquals(0, leg.getStartIndex());
    }

    @Test
    void performThrow() {
        Leg leg = new Leg(0, 2, 501);
        leg.performThrow(new Throw(180, 0, 3, 0));
        assertArrayEquals(new int[]{321, 501}, leg.getPointsLeft());
        assertArrayEquals(new int[]{3, 0}, leg.getDartsThrown());
        assertArrayEquals(new int[]{0, 0}, leg.getDartsOnDouble());
        assertEquals(1, leg.getThrows().size());
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(141, 1, 3, 0));
        assertArrayEquals(new int[]{0, 141}, leg.getPointsLeft());
        assertArrayEquals(new int[]{9, 6}, leg.getDartsThrown());
        assertArrayEquals(new int[]{1, 0}, leg.getDartsOnDouble());
        assertEquals(5, leg.getThrows().size());
    }

    @Test
    void undoThrow() {
        Leg leg = new Leg(0, 2, 501);
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(180, 0, 3, 0));
        Throw t1 = new Throw(180, 0, 3, 1);
        leg.performThrow(t1);
        Throw t2 = new Throw(141, 1, 3, 0);
        leg.performThrow(t2);
        Throw undoneThrow = leg.undoThrow();
        assertEquals(t2.getPoints(), undoneThrow.getPoints());
        assertEquals(t2.getDartsOnDouble(), undoneThrow.getDartsOnDouble());
        assertEquals(t2.getDartsThrown(), undoneThrow.getDartsThrown());
        assertEquals(t2.getPlayerIndex(), undoneThrow.getPlayerIndex());
        undoneThrow = leg.undoThrow();
        assertEquals(t1.getPoints(), undoneThrow.getPoints());
        assertEquals(t1.getDartsOnDouble(), undoneThrow.getDartsOnDouble());
        assertEquals(t1.getDartsThrown(), undoneThrow.getDartsThrown());
        assertEquals(t1.getPlayerIndex(), undoneThrow.getPlayerIndex());

        leg = new Leg(0, 2, 501);
        undoneThrow = leg.undoThrow();
        assertEquals(null, undoneThrow);
    }

    @Test
    void getWinner() {
        Leg leg = new Leg(0, 2, 501);
        leg.performThrow(new Throw(180, 0, 3, 0));
        assertEquals(-1, leg.getWinner());
        leg.performThrow(new Throw(180, 0, 3, 1));
        assertEquals(-1, leg.getWinner());
        leg.performThrow(new Throw(180, 0, 3, 0));
        assertEquals(-1, leg.getWinner());
        leg.performThrow(new Throw(180, 0, 3, 1));
        assertEquals(-1, leg.getWinner());
        leg.performThrow(new Throw(141, 1, 3, 0));
        assertEquals(0, leg.getWinner());
    }

}