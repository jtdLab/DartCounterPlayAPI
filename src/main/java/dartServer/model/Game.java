package dartServer.model;

import dartServer.networking.artefacts.responses.Snapshot;

import java.util.ArrayList;

public class Game {

    private GameConfig config;
    private GameStatus status;

    private ArrayList<Player> players;
    private ArrayList<Set> sets;

    private int turnIndex;

    private GameLoop gameLoop;

    public Game(Player player) {
        this.config = new GameConfig();
        this.status = GameStatus.PENDING;
        players = new ArrayList<>();
        sets = new ArrayList<>();
        turnIndex = 0;
        players.add(player);

        gameLoop = new GameLoop(this);
    }

    public Snapshot getSnapshot() {
        // TODO
        switch (status) {
            case PENDING:
                return new Snapshot();
            case RUNNING:
                return new Snapshot();
        }
        return null;
    }

    public boolean addPlayer(Player player) {
        if(players.size() < 4) {
            players.add(player);
            return true;
        }
        return false;
    }

    public void removePlayer(int index) {
        players.remove(index);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void start() {
        createSet();
        createLeg();
        initPlayers();
        status = GameStatus.RUNNING;
    }

    public boolean performThrow(Throw t) {
        if(true) {
            // TODO THROW VALIDATION
            getCurrentTurn().setNext(false);

            // sets the player who threw
            t.setPlayerIndex(turnIndex);

            // updates the leg data
            getCurrentLeg().performThrow(t);

            // updates the player data
            getCurrentTurn().setLastThrow(t.getPoints());
            getCurrentTurn().setPointsLeft(getCurrentTurn().getPointsLeft() - t.getPoints());
            getCurrentTurn().setDartsThrown(getCurrentTurn().getDartsThrown() + t.getDartsThrown());
            getCurrentTurn().setAverage(getAverageCurrentTurn());
            getCurrentTurn().setCheckoutPercentage(getCheckoutPercentageCurrentTurn());

            // updates the reference to the player who has next turn
            // updates the player data and creates next leg and set when needed
            if(getCurrentLeg().getWinner() != -1) {
                if(getCurrentSet().getWinner() != -1) {
                    int sets = -1;
                    if(config.getType() == GameType.SETS) {
                        sets = getCurrentTurn().getSets() + 1;
                    }
                    int legs;
                    if(config.getType() == GameType.LEGS) {
                        legs = getCurrentTurn().getLegs() + 1;
                    } else {
                        legs = 0;
                    }

                    getCurrentTurn().setPointsLeft(0);
                    getCurrentTurn().setSets(sets);
                    getCurrentTurn().setLegs(legs);
                    if(getWinner() != null) {
                        // GAME FINISHED
                        status = GameStatus.FINISHED;
                    } else {
                        // CONTINUE NEW SET
                        for (int i = 0; i < players.size(); i++) {
                            Player player = players.get(i);
                            player.setPointsLeft(config.getStartingPoints());
                            player.setDartsThrown(0);
                            player.setLegs(0);
                        }
                        turnIndex = (getCurrentSet().getStartIndex() +1) % players.size();
                        createSet();
                        createLeg();
                    }
                } else {
                    // CONTINUE NEW LEG
                    for (int i = 0; i < players.size(); i++) {
                        Player player = players.get(i);
                        int legs = player.getLegs();
                        if(i == turnIndex) {
                            legs +=1;
                        }
                        player.setPointsLeft(config.getStartingPoints());
                        player.setDartsThrown(0);
                        player.setLegs(legs);
                    }
                    turnIndex = (getCurrentLeg().getStartIndex() +1) % players.size();
                    createLeg();
                }
            } else {
                // CONTINUE
                turnIndex = (turnIndex +1) % players.size();
            }
            getCurrentTurn().setNext(true);
            return true;
        }
        return false;
    }

    public void undoThrow() {
        if(sets.size() == 1 && sets.get(0).getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 0) {
            // NO THROW PERFORMED YET -> do nothing
            return;
        }

        getCurrentTurn().setNext(false);

        if(sets.size() == 1 && sets.get(0).getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 1) {
            // UNDO FIRST THROW OF GAME
            Throw last = getCurrentLeg().undoThrow();
            turnIndex = last.getPlayerIndex();
            getCurrentTurn().setLastThrow(-1);
            getCurrentTurn().setPointsLeft(config.getStartingPoints());
            getCurrentTurn().setDartsThrown(0);
            getCurrentTurn().setAverage("0.00");
            getCurrentTurn().setCheckoutPercentage("0.00");
        } else if(sets.size() >= 2 && getCurrentSet().getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 0) {
            // UNDO LAST THROW OF SET
            sets.remove(sets.size()-1);
            Throw last = getCurrentLeg().undoThrow();
            turnIndex = last.getPlayerIndex();

            // restore player data
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);

                if(turnIndex == i) {
                    player.setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size()-players.size()).getPoints());
                    player.setAverage(getAverageCurrentTurn());
                    player.setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
                }

                player.setPointsLeft(getCurrentLeg().getPointsLeft()[i]);
                player.setDartsThrown(getCurrentLeg().getDartsThrown()[i]);

                int s = 0;
                int l = 0;

                for(Set set : sets) {
                    if(config.getType() == GameType.SETS) {
                        if(set.getWinner() == i) {
                            s += 1;
                        }
                    } else {
                        s = -1;
                    }
                }

                for(Leg leg : getCurrentSet().getLegs()) {
                    if(leg.getWinner() == i) {
                        l +=1;
                    }
                }

                player.setSets(s);
                player.setLegs(l);
            }
        } else if(getCurrentSet().getLegs().size() >= 2 && getCurrentLeg().getThrows().size() == 0) {
            // UNDO LAST THROW OF LEG
            getCurrentSet().getLegs().remove(getCurrentSet().getLegs().size() -1);
            Throw last = getCurrentLeg().undoThrow();
            turnIndex = last.getPlayerIndex();

            // restore player data
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);

                if(turnIndex == i) {
                    player.setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size() - players.size()).getPoints());
                    player.setAverage(getAverageCurrentTurn());
                    player.setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
                }

                player.setPointsLeft(getCurrentLeg().getPointsLeft()[i]);
                player.setDartsThrown(getCurrentLeg().getDartsThrown()[i]);

                int l = 0;
                for(Leg leg : getCurrentSet().getLegs()) {
                    if(leg.getWinner() == i) {
                        l += 1;
                    }
                }

                player.setLegs(l);
            }
        } else {
            // UNDO STANDARD THROW
            Throw last = getCurrentLeg().undoThrow();
            turnIndex = last.getPlayerIndex();
            getCurrentTurn().setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size()-players.size()).getPoints());
            getCurrentTurn().setPointsLeft(getCurrentTurn().getPointsLeft() + last.getPoints());
            getCurrentTurn().setDartsThrown(getCurrentTurn().getDartsThrown() - last.getDartsThrown());
        }

        getCurrentTurn().setNext(true);
        getCurrentTurn().setAverage(getAverageCurrentTurn());
        getCurrentTurn().setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
    }

    public String getDescription() {
        return config.getModeAsString() + " " + config.getSize() + " " + config.getTypeAsString();
    }

    private Set getCurrentSet() {
        return sets.get(sets.size()-1);
    }

    private Leg getCurrentLeg() {
        ArrayList<Leg> legs = getCurrentSet().getLegs();
        return legs.get(legs.size() - 1);
    }

    private Player getCurrentTurn() {
        return players.get(turnIndex);
    }

    private String getAverageCurrentTurn() {
        // TODO
        return null;
    }

    private String getCheckoutPercentageCurrentTurn() {
        // TODO
        return null;
    }

    public Player getWinner() {
        // TODO
        return null;
    }

    private void createSet() {
        // TODO
    }

    private void createLeg() {
        // TODO
    }

    private void initPlayers() {
        // TODO
    }


}
