package dartServer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SetTest {

    @Test
    void getWinner() {
        Set set = new Set(0, 1);
        Leg leg = new Leg(0, 2, 501);
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(141, 1, 3, 0));
        set.addLeg(leg);
        assertEquals(0, set.getWinner());

        set = new Set(0, 2);
        leg = new Leg(0, 2, 501);
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(141, 1, 3, 0));
        set.addLeg(leg);
        leg = new Leg(0, 2, 501);
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(141, 1, 3, 1));
        set.addLeg(leg);
        leg = new Leg(0, 2, 501);
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(180, 0, 3, 0));
        leg.performThrow(new Throw(180, 0, 3, 1));
        leg.performThrow(new Throw(141, 1, 3, 0));
        set.addLeg(leg);
        assertEquals(0, set.getWinner());
    }

}