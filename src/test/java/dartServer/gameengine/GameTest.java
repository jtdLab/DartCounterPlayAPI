package dartServer.gameengine;

import dartServer.commons.artifacts.PlayerSnapshot;
import dartServer.gameengine.model.Player;
import dartServer.gameengine.model.Game;
import dartServer.gameengine.model.GameConfig;
import dartServer.gameengine.model.Throw;
import dartServer.gameengine.model.enums.GameMode;
import dartServer.gameengine.model.enums.GameStatus;
import dartServer.gameengine.model.enums.GameType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void addPlayer() {
        Game game = new Game(new Player("Jonas"));
        assertTrue(game.addPlayer(new Player("Tim")));
        assertTrue(game.addPlayer(new Player("David")));
        assertFalse(game.addPlayer(new Player("David")));
        assertTrue(game.addPlayer(new Player("Ronnie")));
        assertFalse(game.addPlayer(new Player("Sergio")));
    }

    @Test
    void removePlayer() {
        Player player1 = new Player("Jonas");
        Game game = new Game(player1);
        assertTrue(game.addPlayer(new Player("Tim")));
        assertTrue(game.addPlayer(new Player("David")));
        assertTrue(game.addPlayer(new Player("Ronnie")));
        assertFalse(game.addPlayer(new Player("Jonas")));
        game.removePlayer(player1);
        Player player = new Player("Jonas");
        assertTrue(game.addPlayer(player));
        assertFalse(game.addPlayer(new Player("Sergio")));
        game.removePlayer(player);
        assertTrue(game.addPlayer(new Player("Sergio")));
    }

    @Test
    void start() {
        Player player1 = new Player("Jonas");
        Player player2 = new Player("David");
        Game game = new Game(player1);
        game.addPlayer(player2);
        assertEquals(GameStatus.PENDING, game.getSnapshot().getStatus());
        //assertEquals("first to 3 legs", game.getSnapshot().getDescription());
        game.start(player1);
        assertEquals(GameStatus.RUNNING, game.getSnapshot().getStatus());
        PlayerSnapshot playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertEquals("Jonas", playerSnapshot1.getName());
        assertTrue(playerSnapshot1.isNext());
        assertEquals(null, playerSnapshot1.getLastThrow());
        assertEquals(501, playerSnapshot1.getPointsLeft());
        assertEquals(0, playerSnapshot1.getDartsThrown());
        assertEquals(null, playerSnapshot1.getSets());
        assertEquals(0, playerSnapshot1.getLegs());
        assertEquals("0.00", playerSnapshot1.getAverage());
        assertEquals("0.00", playerSnapshot1.getCheckoutPercentage());
        PlayerSnapshot playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertEquals("David", playerSnapshot2.getName());
        assertFalse(playerSnapshot2.isNext());
        assertEquals(null, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(0, playerSnapshot2.getDartsThrown());
        assertEquals(null, playerSnapshot2.getSets());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("0.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
    }

    @Test
    void performThrow() {
        Player player1 = new Player("Jonas");
        Player player2 = new Player("David");
        Game game = new Game(player1);
        game.addPlayer(player2);
        game.start(player1);
        assertEquals(GameStatus.RUNNING, game.getSnapshot().getStatus());
        assertFalse(game.performThrow(player2, new Throw(180, 0, 3)));
        // LEG 1
        assertTrue(game.performThrow(player1, new Throw(180, 0, 3)));
        PlayerSnapshot playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertEquals("Jonas", playerSnapshot1.getName());
        assertFalse(playerSnapshot1.isNext());
        assertEquals(180, playerSnapshot1.getLastThrow());
        assertEquals(321, playerSnapshot1.getPointsLeft());
        assertEquals(3, playerSnapshot1.getDartsThrown());
        assertEquals(null, playerSnapshot1.getSets());
        assertEquals(0, playerSnapshot1.getLegs());
        assertEquals("180.00", playerSnapshot1.getAverage());
        assertEquals("0.00", playerSnapshot1.getCheckoutPercentage());
        PlayerSnapshot playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertEquals("David", playerSnapshot2.getName());
        assertTrue(playerSnapshot2.isNext());
        assertEquals(null, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(0, playerSnapshot2.getDartsThrown());
        assertEquals(null, playerSnapshot2.getSets());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("0.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        game.performThrow(player2, new Throw(180, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertTrue(playerSnapshot1.isNext());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertFalse(playerSnapshot2.isNext());
        assertEquals(180, playerSnapshot2.getLastThrow());
        assertEquals(321, playerSnapshot2.getPointsLeft());
        assertEquals(3, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("180.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        game.performThrow(player1, new Throw(180, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertFalse(playerSnapshot1.isNext());
        assertEquals(180, playerSnapshot1.getLastThrow());
        assertEquals(141, playerSnapshot1.getPointsLeft());
        assertEquals(6, playerSnapshot1.getDartsThrown());
        assertEquals(0, playerSnapshot1.getLegs());
        assertEquals("180.00", playerSnapshot1.getAverage());
        assertEquals("0.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertTrue(playerSnapshot2.isNext());
        game.performThrow(player2, new Throw(180, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertTrue(playerSnapshot1.isNext());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertFalse(playerSnapshot2.isNext());
        assertEquals(180, playerSnapshot2.getLastThrow());
        assertEquals(141, playerSnapshot2.getPointsLeft());
        assertEquals(6, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("180.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        game.performThrow(player1, new Throw(141, 1, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertFalse(playerSnapshot1.isNext());
        assertEquals(141, playerSnapshot1.getLastThrow());
        assertEquals(501, playerSnapshot1.getPointsLeft());
        assertEquals(0, playerSnapshot1.getDartsThrown());
        assertEquals(1, playerSnapshot1.getLegs());
        assertEquals("167.00", playerSnapshot1.getAverage());
        assertEquals("100.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertTrue(playerSnapshot2.isNext());
        assertEquals(180, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(0, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("180.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());

        assertEquals(GameStatus.RUNNING, game.getSnapshot().getStatus());
        assertFalse(game.performThrow(player1, new Throw(180, 0, 3)));
        // LEG 2
        assertTrue(game.performThrow(player2, new Throw(0, 0, 3)));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertTrue(playerSnapshot1.isNext());
        assertEquals(141, playerSnapshot1.getLastThrow());
        assertEquals(501, playerSnapshot1.getPointsLeft());
        assertEquals(0, playerSnapshot1.getDartsThrown());
        assertEquals(1, playerSnapshot1.getLegs());
        assertEquals("167.00", playerSnapshot1.getAverage());
        assertEquals("100.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertFalse(playerSnapshot2.isNext());
        assertEquals(0, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(3, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("120.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        game.performThrow(player1, new Throw(180, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertFalse(playerSnapshot1.isNext());
        assertEquals(180, playerSnapshot1.getLastThrow());
        assertEquals(321, playerSnapshot1.getPointsLeft());
        assertEquals(3, playerSnapshot1.getDartsThrown());
        assertEquals(1, playerSnapshot1.getLegs());
        assertEquals("170.25", playerSnapshot1.getAverage());
        assertEquals("100.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertTrue(playerSnapshot2.isNext());
        game.performThrow(player2, new Throw(120, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertTrue(playerSnapshot1.isNext());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertFalse(playerSnapshot2.isNext());
        assertEquals(120, playerSnapshot2.getLastThrow());
        assertEquals(381, playerSnapshot2.getPointsLeft());
        assertEquals(6, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("120.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        game.performThrow(player1, new Throw(180, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertFalse(playerSnapshot1.isNext());
        assertEquals(180, playerSnapshot1.getLastThrow());
        assertEquals(141, playerSnapshot1.getPointsLeft());
        assertEquals(6, playerSnapshot1.getDartsThrown());
        assertEquals(1, playerSnapshot1.getLegs());
        assertEquals("172.20", playerSnapshot1.getAverage());
        assertEquals("100.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertTrue(playerSnapshot2.isNext());
        game.performThrow(player2, new Throw(120, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertTrue(playerSnapshot1.isNext());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertFalse(playerSnapshot2.isNext());
        assertEquals(120, playerSnapshot2.getLastThrow());
        assertEquals(261, playerSnapshot2.getPointsLeft());
        assertEquals(9, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("120.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        game.performThrow(player1, new Throw(141, 1, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertTrue(playerSnapshot1.isNext());
        assertEquals(141, playerSnapshot1.getLastThrow());
        assertEquals(501, playerSnapshot1.getPointsLeft());
        assertEquals(0, playerSnapshot1.getDartsThrown());
        assertEquals(2, playerSnapshot1.getLegs());
        assertEquals("167.00", playerSnapshot1.getAverage());
        assertEquals("100.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertFalse(playerSnapshot2.isNext());
        assertEquals(120, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(0, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("120.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());

        assertEquals(GameStatus.RUNNING, game.getSnapshot().getStatus());
        assertFalse(game.performThrow(player2, new Throw(180, 0, 3)));
        // LEG 3
        assertTrue(game.performThrow(player1, new Throw(167, 0, 3)));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertEquals("Jonas", playerSnapshot1.getName());
        assertFalse(playerSnapshot1.isNext());
        assertEquals(167, playerSnapshot1.getLastThrow());
        assertEquals(334, playerSnapshot1.getPointsLeft());
        assertEquals(3, playerSnapshot1.getDartsThrown());
        assertEquals(null, playerSnapshot1.getSets());
        assertEquals(2, playerSnapshot1.getLegs());
        assertEquals("167.00", playerSnapshot1.getAverage());
        assertEquals("100.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertEquals("David", playerSnapshot2.getName());
        assertTrue(playerSnapshot2.isNext());
        assertEquals(120, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(0, playerSnapshot2.getDartsThrown());
        assertEquals(null, playerSnapshot2.getSets());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("120.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        game.performThrow(player2, new Throw(120, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertTrue(playerSnapshot1.isNext());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertFalse(playerSnapshot2.isNext());
        assertEquals(120, playerSnapshot2.getLastThrow());
        assertEquals(381, playerSnapshot2.getPointsLeft());
        assertEquals(3, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("120.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        game.performThrow(player1, new Throw(167, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertFalse(playerSnapshot1.isNext());
        assertEquals(167, playerSnapshot1.getLastThrow());
        assertEquals(167, playerSnapshot1.getPointsLeft());
        assertEquals(6, playerSnapshot1.getDartsThrown());
        assertEquals(2, playerSnapshot1.getLegs());
        assertEquals("167.00", playerSnapshot1.getAverage());
        assertEquals("100.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertTrue(playerSnapshot2.isNext());
        game.performThrow(player2, new Throw(120, 0, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertTrue(playerSnapshot1.isNext());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertFalse(playerSnapshot2.isNext());
        assertEquals(120, playerSnapshot2.getLastThrow());
        assertEquals(261, playerSnapshot2.getPointsLeft());
        assertEquals(6, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("120.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        game.performThrow(player1, new Throw(167, 1, 3));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertTrue(playerSnapshot1.isNext()); // TODO
        assertEquals(167, playerSnapshot1.getLastThrow());
        assertEquals(0, playerSnapshot1.getPointsLeft());
        assertEquals(9, playerSnapshot1.getDartsThrown());
        assertEquals(3, playerSnapshot1.getLegs());
        assertEquals("167.00", playerSnapshot1.getAverage());
        assertEquals("100.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertFalse(playerSnapshot2.isNext()); // TODO
        assertEquals(120, playerSnapshot2.getLastThrow());
        assertEquals(261, playerSnapshot2.getPointsLeft());
        assertEquals(6, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("120.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());
        assertEquals(GameStatus.FINISHED, game.getSnapshot().getStatus());
        assertEquals(player1, game.getWinner());
    }

    @Test
    void undoThrow() {
        // first throw game
        Player player1 = new Player("Jonas");
        Player player2 = new Player("David");
        Game game = new Game(player1);
        game.addPlayer(player2);
        GameConfig config = new GameConfig(GameMode.FIRST_TO, GameType.LEGS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.undoThrow(player1);
        PlayerSnapshot playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertEquals("Jonas", playerSnapshot1.getName());
        assertTrue(playerSnapshot1.isNext());
        assertEquals(null, playerSnapshot1.getLastThrow());
        assertEquals(501, playerSnapshot1.getPointsLeft());
        assertEquals(0, playerSnapshot1.getDartsThrown());
        assertEquals(null, playerSnapshot1.getSets());
        assertEquals(0, playerSnapshot1.getLegs());
        assertEquals("0.00", playerSnapshot1.getAverage());
        assertEquals("0.00", playerSnapshot1.getCheckoutPercentage());
        PlayerSnapshot playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertEquals("David", playerSnapshot2.getName());
        assertFalse(playerSnapshot2.isNext());
        assertEquals(null, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(0, playerSnapshot2.getDartsThrown());
        assertEquals(null, playerSnapshot2.getSets());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("0.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());

        // undo last throw of finished leg in leg mode
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.FIRST_TO, GameType.LEGS, 2, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(180, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(180, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(141, 1, 3));
        assertTrue(game.undoThrow(player1));
        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertEquals("Jonas", playerSnapshot1.getName());
        assertTrue(playerSnapshot1.isNext());
        assertEquals(180, playerSnapshot1.getLastThrow());
        assertEquals(141, playerSnapshot1.getPointsLeft());
        assertEquals(6, playerSnapshot1.getDartsThrown());
        assertEquals(null, playerSnapshot1.getSets());
        assertEquals(0, playerSnapshot1.getLegs());
        assertEquals("180.00", playerSnapshot1.getAverage());
        assertEquals("0.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertEquals("David", playerSnapshot2.getName());
        assertFalse(playerSnapshot2.isNext());
        assertEquals(0, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(6, playerSnapshot2.getDartsThrown());
        assertEquals(null, playerSnapshot2.getSets());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("0.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());

        // first throw first leg set mode >=2nd set
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.FIRST_TO, GameType.SETS, 2, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(180, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(180, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(141, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(180, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(180, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(141, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        assertTrue(game.undoThrow(player1));

        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertEquals("Jonas", playerSnapshot1.getName());
        assertTrue(playerSnapshot1.isNext());
        assertEquals(167, playerSnapshot1.getLastThrow());
        assertEquals(167, playerSnapshot1.getPointsLeft());
        assertEquals(6, playerSnapshot1.getDartsThrown());
        assertEquals(0, playerSnapshot1.getSets());
        assertEquals(2, playerSnapshot1.getLegs());
        assertEquals("167.00", playerSnapshot1.getAverage());
        assertEquals("100.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertEquals("David", playerSnapshot2.getName());
        assertFalse(playerSnapshot2.isNext());
        assertEquals(0, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(6, playerSnapshot2.getDartsThrown());
        assertEquals(0, playerSnapshot2.getSets());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("0.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());

        // last throw after game is finished
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.FIRST_TO, GameType.LEGS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));
        assertEquals(player1, game.getWinner());
        assertFalse(game.undoThrow(player1));

        // multiple undos ( more undos than throws per leg)
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.FIRST_TO, GameType.LEGS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(100, 0, 3));
        game.performThrow(player2, new Throw(50, 0, 3));
        game.performThrow(player1, new Throw(100, 0, 3));
        game.performThrow(player2, new Throw(50, 0, 3));
        game.performThrow(player1, new Throw(100, 0, 3));
        game.undoThrow(player1);
        game.undoThrow(player2);
        game.undoThrow(player1);
        game.undoThrow(player2);
        game.undoThrow(player1);

        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertEquals("Jonas", playerSnapshot1.getName());
        assertTrue(playerSnapshot1.isNext());
        assertEquals(null, playerSnapshot1.getLastThrow());
        assertEquals(501, playerSnapshot1.getPointsLeft());
        assertEquals(0, playerSnapshot1.getDartsThrown());
        assertEquals(null, playerSnapshot1.getSets());
        assertEquals(0, playerSnapshot1.getLegs());
        assertEquals("0.00", playerSnapshot1.getAverage());
        assertEquals("0.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertEquals("David", playerSnapshot2.getName());
        assertFalse(playerSnapshot2.isNext());
        assertEquals(null, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(0, playerSnapshot2.getDartsThrown());
        assertEquals(null, playerSnapshot2.getSets());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("0.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());

        assertFalse(game.undoThrow(player2));
        assertFalse(game.undoThrow(player1));

        // multiple undos ( more undos than throws per leg) > 2 Players
        player1 = new Player("Jonas");
        player2 = new Player("David");
        Player player3 = new Player("Pontius");
        game = new Game(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        config = new GameConfig(GameMode.FIRST_TO, GameType.LEGS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(100, 0, 3));
        game.performThrow(player2, new Throw(50, 0, 3));
        game.performThrow(player3, new Throw(25, 0, 3));
        game.performThrow(player1, new Throw(100, 0, 3));
        game.performThrow(player2, new Throw(50, 0, 3));
        game.performThrow(player3, new Throw(25, 0, 3));
        game.performThrow(player1, new Throw(100, 0, 3));
        game.undoThrow(player1);
        game.undoThrow(player3);
        game.undoThrow(player2);
        game.undoThrow(player1);
        game.undoThrow(player3);
        game.undoThrow(player2);
        game.undoThrow(player1);

        playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertEquals("Jonas", playerSnapshot1.getName());
        assertTrue(playerSnapshot1.isNext());
        assertEquals(null, playerSnapshot1.getLastThrow());
        assertEquals(501, playerSnapshot1.getPointsLeft());
        assertEquals(0, playerSnapshot1.getDartsThrown());
        assertEquals(null, playerSnapshot1.getSets());
        assertEquals(0, playerSnapshot1.getLegs());
        assertEquals("0.00", playerSnapshot1.getAverage());
        assertEquals("0.00", playerSnapshot1.getCheckoutPercentage());
        playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertEquals("David", playerSnapshot2.getName());
        assertFalse(playerSnapshot2.isNext());
        assertEquals(null, playerSnapshot2.getLastThrow());
        assertEquals(501, playerSnapshot2.getPointsLeft());
        assertEquals(0, playerSnapshot2.getDartsThrown());
        assertEquals(null, playerSnapshot2.getSets());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals("0.00", playerSnapshot2.getAverage());
        assertEquals("0.00", playerSnapshot2.getCheckoutPercentage());

        PlayerSnapshot playerSnapshot3 = game.getSnapshot().getPlayers().get(2);
        assertEquals("Pontius", playerSnapshot3.getName());
        assertFalse(playerSnapshot3.isNext());
        assertEquals(null, playerSnapshot3.getLastThrow());
        assertEquals(501, playerSnapshot3.getPointsLeft());
        assertEquals(0, playerSnapshot3.getDartsThrown());
        assertEquals(null, playerSnapshot3.getSets());
        assertEquals(0, playerSnapshot3.getLegs());
        assertEquals("0.00", playerSnapshot3.getAverage());
        assertEquals("0.00", playerSnapshot3.getCheckoutPercentage());

        assertFalse(game.undoThrow(player3));
        assertFalse(game.undoThrow(player2));
        assertFalse(game.undoThrow(player1));
    }

    @Test
    void getWinner() {
        // FIRST TO 1 LEG
        Player player1 = new Player("Jonas");
        Player player2 = new Player("David");
        Game game = new Game(player1);
        game.addPlayer(player2);
        GameConfig config = new GameConfig(GameMode.FIRST_TO, GameType.LEGS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));
        assertEquals(player1, game.getWinner());


        // FIRST TO 3 LEGS
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        game.start(player1);
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));
        assertEquals(player1, game.getWinner());


        // FIRST TO 1 SET
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.FIRST_TO, GameType.SETS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));
        assertEquals(player1, game.getWinner());


        // FIRST TO 3 SETS
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.FIRST_TO, GameType.SETS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));
        assertEquals(player1, game.getWinner());


        // BEST OF 1 LEG
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.BEST_OF, GameType.LEGS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));
        assertEquals(player1, game.getWinner());


        // BEST OF 3 LEGS
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.BEST_OF, GameType.LEGS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));
        assertEquals(player1, game.getWinner());


        // BEST OF 1 SET
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.BEST_OF, GameType.SETS, 1, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));
        assertEquals(player1, game.getWinner());


        // BEST OF 3 SETS
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.BEST_OF, GameType.SETS, 3, 501);
        game.updateConfig(player1, config);
        game.start(player1);
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));

        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 0, 3));
        game.performThrow(player2, new Throw(0, 0, 3));
        game.performThrow(player1, new Throw(167, 1, 3));
        assertEquals(player1, game.getWinner());
    }

    @Test
    void getSnapshot() {
        Player player1 = new Player("Jonas");
        Player player2 = new Player("David");
        Game game = new Game(player1);
        game.addPlayer(player2);
        assertEquals(GameStatus.PENDING, game.getSnapshot().getStatus());
        //assertEquals("first to 3 legs", game.getSnapshot().getDescription());
        PlayerSnapshot playerSnapshot1 = game.getSnapshot().getPlayers().get(0);
        assertEquals("Jonas", playerSnapshot1.getName());
        assertFalse(playerSnapshot1.isNext());
        assertEquals(null, playerSnapshot1.getLastThrow());
        assertEquals(0, playerSnapshot1.getPointsLeft());
        assertEquals(0, playerSnapshot1.getDartsThrown());
        assertEquals(null, playerSnapshot1.getSets());
        assertEquals(0, playerSnapshot1.getLegs());
        assertEquals(null, playerSnapshot1.getAverage());
        assertEquals(null, playerSnapshot1.getCheckoutPercentage());
        PlayerSnapshot playerSnapshot2 = game.getSnapshot().getPlayers().get(1);
        assertEquals("David", playerSnapshot2.getName());
        assertFalse(playerSnapshot2.isNext());
        assertEquals(null, playerSnapshot2.getLastThrow());
        assertEquals(0, playerSnapshot2.getPointsLeft());
        assertEquals(0, playerSnapshot2.getDartsThrown());
        assertEquals(null, playerSnapshot2.getSets());
        assertEquals(0, playerSnapshot2.getLegs());
        assertEquals(null, playerSnapshot2.getAverage());
        assertEquals(null, playerSnapshot2.getCheckoutPercentage());
    }

    @Test
    void getDescription() {
        Game game = new Game(new Player("Jonas"));
        //assertEquals("first to 3 legs", game.getSnapshot().getDescription());
    }

}