package dartServer.commons.artifacts;

import dartServer.gameengine.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtifactsTest {

  /*  @Test
    public void testToString() {
        PlayerSnapshot playerSnapshot1 = new Player("Mrjosch99", null).getSnapshot();
        assertEquals("PlayerSnapshot{name='Mrjosch99', isNext=false, lastThrow=null, pointsLeft=0, dartsThrown=0, sets=null, legs=0, average='null', checkoutPercentage='null'}",
                playerSnapshot1.toString());
        PlayerSnapshot playerSnapshot2 = new Player("Needs00", null).getSnapshot();
        //assertEquals("GameSnapshot{status=RUNNING, description='first to 3 legs', players=[PlayerSnapshot{name='Mrjosch99', isNext=false, lastThrow=null, pointsLeft=0, dartsThrown=0, sets=null, legs=0, average='null', checkoutPercentage='null'}, PlayerSnapshot{name='Needs00', isNext=false, lastThrow=null, pointsLeft=0, dartsThrown=0, sets=null, legs=0, average='null', checkoutPercentage='null'}]}",
         //       new GameSnapshot(GameStatus.RUNNING, "first to 3 legs", List.of(playerSnapshot1, playerSnapshot2)).toString());
    }

    @Test
    public void testEquals() {
        Player player1 = new Player("mrjosch99", null);
        Player player2 = new Player("needs00", null);
        assertEquals(new PlayerSnapshot(player1), new PlayerSnapshot(player1));
        //assertEquals(new GameSnapshot(GameStatus.RUNNING, "first to 3 legs", List.of(player1.getSnapshot(), player2.getSnapshot())), new GameSnapshot(GameStatus.RUNNING, "first to 3 legs", List.of(player1.getSnapshot(), player2.getSnapshot())));
    }*/

}
