package dartServer.gameengine;

class GameTest {
/*
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
        Game game = new Game(new Player("Jonas"));
        assertTrue(game.addPlayer(new Player("Tim")));
        assertTrue(game.addPlayer(new Player("David")));
        assertTrue(game.addPlayer(new Player("Ronnie")));
        assertFalse(game.addPlayer(new Player("Jonas")));
        game.removePlayer(0);
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
        assertEquals("pending", game.getSnapshot().status);
        assertEquals("first to 3 legs", game.getSnapshot().description);
        game.start();
        assertEquals("running", game.getSnapshot().status);
        PlayerSnapshot playerSnapshot1 = game.getSnapshot().players.get(0);
        assertEquals("Jonas", playerSnapshot1.name);
        assertTrue(playerSnapshot1.isNext);
        assertEquals(-1, playerSnapshot1.lastThrow);
        assertEquals(501, playerSnapshot1.pointsLeft);
        assertEquals(0, playerSnapshot1.dartsThrown);
        assertEquals(-1, playerSnapshot1.sets);
        assertEquals(0, playerSnapshot1.legs);
        assertEquals("0.00", playerSnapshot1.average);
        assertEquals("0.00", playerSnapshot1.checkoutPercentage);
        PlayerSnapshot playerSnapshot2 = game.getSnapshot().players.get(1);
        assertEquals("David", playerSnapshot2.name);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(-1, playerSnapshot2.lastThrow);
        assertEquals(501, playerSnapshot2.pointsLeft);
        assertEquals(0, playerSnapshot2.dartsThrown);
        assertEquals(-1, playerSnapshot2.sets);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("0.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
    }

    @Test
    void performThrow() {
        Player player1 = new Player("Jonas");
        Player player2 = new Player("David");
        Game game = new Game(player1);
        game.addPlayer(player2);
        game.start();
        assertEquals("running", game.getSnapshot().status);
        assertFalse(game.performThrow(new Throw(180, 0, 3, 1)));
        // LEG 1
        assertTrue(game.performThrow(new Throw(180, 0, 3, 0)));
        PlayerSnapshot playerSnapshot1 = game.getSnapshot().players.get(0);
        assertEquals("Jonas", playerSnapshot1.name);
        assertFalse(playerSnapshot1.isNext);
        assertEquals(180, playerSnapshot1.lastThrow);
        assertEquals(321, playerSnapshot1.pointsLeft);
        assertEquals(3, playerSnapshot1.dartsThrown);
        assertEquals(-1, playerSnapshot1.sets);
        assertEquals(0, playerSnapshot1.legs);
        assertEquals("180.00", playerSnapshot1.average);
        assertEquals("0.00", playerSnapshot1.checkoutPercentage);
        PlayerSnapshot playerSnapshot2 = game.getSnapshot().players.get(1);
        assertEquals("David", playerSnapshot2.name);
        assertTrue(playerSnapshot2.isNext);
        assertEquals(-1, playerSnapshot2.lastThrow);
        assertEquals(501, playerSnapshot2.pointsLeft);
        assertEquals(0, playerSnapshot2.dartsThrown);
        assertEquals(-1, playerSnapshot2.sets);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("0.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        game.performThrow(new Throw(180, 0, 3, 1));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertTrue(playerSnapshot1.isNext);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(180, playerSnapshot2.lastThrow);
        assertEquals(321, playerSnapshot2.pointsLeft);
        assertEquals(3, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("180.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        game.performThrow(new Throw(180, 0, 3, 0));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertFalse(playerSnapshot1.isNext);
        assertEquals(180, playerSnapshot1.lastThrow);
        assertEquals(141, playerSnapshot1.pointsLeft);
        assertEquals(6, playerSnapshot1.dartsThrown);
        assertEquals(0, playerSnapshot1.legs);
        assertEquals("180.00", playerSnapshot1.average);
        assertEquals("0.00", playerSnapshot1.checkoutPercentage);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertTrue(playerSnapshot2.isNext);
        game.performThrow(new Throw(180, 0, 3, 1));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertTrue(playerSnapshot1.isNext);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(180, playerSnapshot2.lastThrow);
        assertEquals(141, playerSnapshot2.pointsLeft);
        assertEquals(6, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("180.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        game.performThrow(new Throw(141, 1, 3, 0));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertFalse(playerSnapshot1.isNext);
        assertEquals(141, playerSnapshot1.lastThrow);
        assertEquals(501, playerSnapshot1.pointsLeft);
        assertEquals(0, playerSnapshot1.dartsThrown);
        assertEquals(1, playerSnapshot1.legs);
        assertEquals("167.00", playerSnapshot1.average);
        assertEquals("100.00", playerSnapshot1.checkoutPercentage);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertTrue(playerSnapshot2.isNext);
        assertEquals(180, playerSnapshot2.lastThrow);
        assertEquals(501, playerSnapshot2.pointsLeft);
        assertEquals(0, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("180.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);

        assertEquals("running", game.getSnapshot().status);
        assertFalse(game.performThrow(new Throw(180, 0, 3, 0)));
        // LEG 2
        assertTrue(game.performThrow(new Throw(0, 0, 3, 1)));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertTrue(playerSnapshot1.isNext);
        assertEquals(141, playerSnapshot1.lastThrow);
        assertEquals(501, playerSnapshot1.pointsLeft);
        assertEquals(0, playerSnapshot1.dartsThrown);
        assertEquals(1, playerSnapshot1.legs);
        assertEquals("167.00", playerSnapshot1.average);
        assertEquals("100.00", playerSnapshot1.checkoutPercentage);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(0, playerSnapshot2.lastThrow);
        assertEquals(501, playerSnapshot2.pointsLeft);
        assertEquals(3, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("120.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        game.performThrow(new Throw(180, 0, 3, 0));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertFalse(playerSnapshot1.isNext);
        assertEquals(180, playerSnapshot1.lastThrow);
        assertEquals(321, playerSnapshot1.pointsLeft);
        assertEquals(3, playerSnapshot1.dartsThrown);
        assertEquals(1, playerSnapshot1.legs);
        assertEquals("170.25", playerSnapshot1.average);
        assertEquals("100.00", playerSnapshot1.checkoutPercentage);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertTrue(playerSnapshot2.isNext);
        game.performThrow(new Throw(120, 0, 3, 1));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertTrue(playerSnapshot1.isNext);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(120, playerSnapshot2.lastThrow);
        assertEquals(381, playerSnapshot2.pointsLeft);
        assertEquals(6, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("120.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        game.performThrow(new Throw(180, 0, 3, 0));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertFalse(playerSnapshot1.isNext);
        assertEquals(180, playerSnapshot1.lastThrow);
        assertEquals(141, playerSnapshot1.pointsLeft);
        assertEquals(6, playerSnapshot1.dartsThrown);
        assertEquals(1, playerSnapshot1.legs);
        assertEquals("172.20", playerSnapshot1.average);
        assertEquals("100.00", playerSnapshot1.checkoutPercentage);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertTrue(playerSnapshot2.isNext);
        game.performThrow(new Throw(120, 0, 3, 1));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertTrue(playerSnapshot1.isNext);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(120, playerSnapshot2.lastThrow);
        assertEquals(261, playerSnapshot2.pointsLeft);
        assertEquals(9, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("120.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        game.performThrow(new Throw(141, 1, 3, 0));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertTrue(playerSnapshot1.isNext);
        assertEquals(141, playerSnapshot1.lastThrow);
        assertEquals(501, playerSnapshot1.pointsLeft);
        assertEquals(0, playerSnapshot1.dartsThrown);
        assertEquals(2, playerSnapshot1.legs);
        assertEquals("167.00", playerSnapshot1.average);
        assertEquals("100.00", playerSnapshot1.checkoutPercentage);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(120, playerSnapshot2.lastThrow);
        assertEquals(501, playerSnapshot2.pointsLeft);
        assertEquals(0, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("120.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);

        assertEquals("running", game.getSnapshot().status);
        assertFalse(game.performThrow(new Throw(180, 0, 3, 1)));
        // LEG 3
        assertTrue(game.performThrow(new Throw(167, 0, 3, 0)));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertEquals("Jonas", playerSnapshot1.name);
        assertFalse(playerSnapshot1.isNext);
        assertEquals(167, playerSnapshot1.lastThrow);
        assertEquals(334, playerSnapshot1.pointsLeft);
        assertEquals(3, playerSnapshot1.dartsThrown);
        assertEquals(-1, playerSnapshot1.sets);
        assertEquals(2, playerSnapshot1.legs);
        assertEquals("167.00", playerSnapshot1.average);
        assertEquals("100.00", playerSnapshot1.checkoutPercentage);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertEquals("David", playerSnapshot2.name);
        assertTrue(playerSnapshot2.isNext);
        assertEquals(120, playerSnapshot2.lastThrow);
        assertEquals(501, playerSnapshot2.pointsLeft);
        assertEquals(0, playerSnapshot2.dartsThrown);
        assertEquals(-1, playerSnapshot2.sets);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("120.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        game.performThrow(new Throw(120, 0, 3, 1));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertTrue(playerSnapshot1.isNext);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(120, playerSnapshot2.lastThrow);
        assertEquals(381, playerSnapshot2.pointsLeft);
        assertEquals(3, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("120.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        game.performThrow(new Throw(167, 0, 3, 0));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertFalse(playerSnapshot1.isNext);
        assertEquals(167, playerSnapshot1.lastThrow);
        assertEquals(167, playerSnapshot1.pointsLeft);
        assertEquals(6, playerSnapshot1.dartsThrown);
        assertEquals(2, playerSnapshot1.legs);
        assertEquals("167.00", playerSnapshot1.average);
        assertEquals("100.00", playerSnapshot1.checkoutPercentage);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertTrue(playerSnapshot2.isNext);
        game.performThrow(new Throw(120, 0, 3, 1));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertTrue(playerSnapshot1.isNext);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(120, playerSnapshot2.lastThrow);
        assertEquals(261, playerSnapshot2.pointsLeft);
        assertEquals(6, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("120.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        game.performThrow(new Throw(167, 1, 3, 0));
        playerSnapshot1 = game.getSnapshot().players.get(0);
        assertTrue(playerSnapshot1.isNext); // TODO
        assertEquals(167, playerSnapshot1.lastThrow);
        assertEquals(0, playerSnapshot1.pointsLeft);
        assertEquals(9, playerSnapshot1.dartsThrown);
        assertEquals(3, playerSnapshot1.legs);
        assertEquals("167.00", playerSnapshot1.average);
        assertEquals("100.00", playerSnapshot1.checkoutPercentage);
        playerSnapshot2 = game.getSnapshot().players.get(1);
        assertFalse(playerSnapshot2.isNext); // TODO
        assertEquals(120, playerSnapshot2.lastThrow);
        assertEquals(261, playerSnapshot2.pointsLeft);
        assertEquals(6, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("120.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);
        assertEquals("finished", game.getSnapshot().status);
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
        game.setConfig(config);
        game.start();
        game.undoThrow();
        PlayerSnapshot playerSnapshot1 = game.getSnapshot().players.get(0);
        assertEquals("Jonas", playerSnapshot1.name);
        assertTrue(playerSnapshot1.isNext);
        assertEquals(-1, playerSnapshot1.lastThrow);
        assertEquals(501, playerSnapshot1.pointsLeft);
        assertEquals(0, playerSnapshot1.dartsThrown);
        assertEquals(-1, playerSnapshot1.sets);
        assertEquals(0, playerSnapshot1.legs);
        assertEquals("0.00", playerSnapshot1.average);
        assertEquals("0.00", playerSnapshot1.checkoutPercentage);
        PlayerSnapshot playerSnapshot2 = game.getSnapshot().players.get(1);
        assertEquals("David", playerSnapshot2.name);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(-1, playerSnapshot2.lastThrow);
        assertEquals(501, playerSnapshot2.pointsLeft);
        assertEquals(0, playerSnapshot2.dartsThrown);
        assertEquals(-1, playerSnapshot2.sets);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals("0.00", playerSnapshot2.average);
        assertEquals("0.00", playerSnapshot2.checkoutPercentage);

        // first throw > 2nd leg in leg mode
        // TODO

        // first throw first leg set mode >2n set
        // TODO

        // last throw after game is finished
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.FIRST_TO, GameType.LEGS, 1, 501);
        game.setConfig(config);
        game.start();
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));
        assertEquals(player1, game.getWinner());
        // TODO multiple undos ( more undos than throws per leg)
        assertEquals(player1, game.getWinner());
    }

    @Test
    void getWinner() {
        // FIRST TO 1 LEG
        Player player1 = new Player("Jonas");
        Player player2 = new Player("David");
        Game game = new Game(player1);
        game.addPlayer(player2);
        GameConfig config = new GameConfig(GameMode.FIRST_TO, GameType.LEGS, 1, 501);
        game.setConfig(config);
        game.start();
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));
        assertEquals(player1, game.getWinner());


        // FIRST TO 3 LEGS
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        game.start();
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));
        assertEquals(player1, game.getWinner());


        // FIRST TO 1 SET
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.FIRST_TO, GameType.SETS, 1, 501);
        game.setConfig(config);
        game.start();
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));
        assertEquals(player1, game.getWinner());


        // FIRST TO 3 SETS
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.FIRST_TO, GameType.SETS, 1, 501);
        game.setConfig(config);
        game.start();
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));
        assertEquals(player1, game.getWinner());


        // BEST OF 1 LEG
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.BEST_OF, GameType.LEGS, 1, 501);
        game.setConfig(config);
        game.start();
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));
        assertEquals(player1, game.getWinner());


        // BEST OF 3 LEGS
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.BEST_OF, GameType.LEGS, 1, 501);
        game.setConfig(config);
        game.start();
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));
        assertEquals(player1, game.getWinner());


        // BEST OF 1 SET
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.BEST_OF, GameType.SETS, 1, 501);
        game.setConfig(config);
        game.start();
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));
        assertEquals(player1, game.getWinner());


        // BEST OF 3 SETS
        player1 = new Player("Jonas");
        player2 = new Player("David");
        game = new Game(player1);
        game.addPlayer(player2);
        config = new GameConfig(GameMode.BEST_OF, GameType.SETS, 3, 501);
        game.setConfig(config);
        game.start();
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));

        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 0, 3, 0));
        game.performThrow(new Throw(0, 0, 3, 1));
        game.performThrow(new Throw(167, 1, 3, 0));
        assertEquals(player1, game.getWinner());
    }

    @Test
    void getSnapshot() {
        Player player1 = new Player("Jonas");
        Player player2 = new Player("David");
        Game game = new Game(player1);
        game.addPlayer(player2);
        assertEquals("pending", game.getSnapshot().status);
        assertEquals("first to 3 legs", game.getSnapshot().description);
        PlayerSnapshot playerSnapshot1 = game.getSnapshot().players.get(0);
        assertEquals("Jonas", playerSnapshot1.name);
        assertFalse(playerSnapshot1.isNext);
        assertEquals(0, playerSnapshot1.lastThrow);
        assertEquals(0, playerSnapshot1.pointsLeft);
        assertEquals(0, playerSnapshot1.dartsThrown);
        assertEquals(0, playerSnapshot1.sets);
        assertEquals(0, playerSnapshot1.legs);
        assertEquals(null, playerSnapshot1.average);
        assertEquals(null, playerSnapshot1.checkoutPercentage);
        PlayerSnapshot playerSnapshot2 = game.getSnapshot().players.get(1);
        assertEquals("David", playerSnapshot2.name);
        assertFalse(playerSnapshot2.isNext);
        assertEquals(0, playerSnapshot2.lastThrow);
        assertEquals(0, playerSnapshot2.pointsLeft);
        assertEquals(0, playerSnapshot2.dartsThrown);
        assertEquals(0, playerSnapshot2.sets);
        assertEquals(0, playerSnapshot2.legs);
        assertEquals(null, playerSnapshot2.average);
        assertEquals(null, playerSnapshot2.checkoutPercentage);
    }

    @Test
    void getDescription() {
        Game game = new Game(new Player("Jonas"));
        assertEquals("first to 3 legs", game.getDescription());
    }


 */
}