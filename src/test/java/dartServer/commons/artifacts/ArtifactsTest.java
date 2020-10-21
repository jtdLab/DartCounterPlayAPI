package dartServer.commons.artifacts;

import dartServer.gameengine.lobby.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtifactsTest {

    @Test
    public void testToString() {
        // TODO
    }

    @Test
    public void testEquals() {
        Player player1 = new Player("mrjosch99", null);
        Player player2 = new Player("needs00", null);
        assertEquals(new PlayerSnapshot(player1), new PlayerSnapshot(player1));
        assertEquals(new GameSnapshot("running", "first to 3 legs", List.of(player1.getSnapshot(), player2.getSnapshot())), new GameSnapshot("running", "first to 3 legs", List.of(player1.getSnapshot(), player2.getSnapshot())));
    }

}
