package dartServer.model;

import dartServer.model.enums.GameMode;
import dartServer.model.enums.GameStatus;
import dartServer.model.enums.GameType;
import dartServer.networking.artefacts.responses.snapshots.GameSnapshot;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game {

    private GameStatus status;
    private GameConfig config;

    private ArrayList<Player> players;
    private ArrayList<Set> sets;

    private int turnIndex;

    public Game(Player player) {
        this.config = new GameConfig();
        this.status = GameStatus.PENDING;
        players = new ArrayList<>();
        sets = new ArrayList<>();
        turnIndex = 0;
        players.add(player);
    }


    public boolean addPlayer(Player player) {
        if (players.size() < 4) {
            for (Player p : players) {
                if (p.getName().equals(player.getName())) return false;
            }
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
        if (turnIndex == t.getPlayerIndex() && ThrowValidator.isValidThrow(t, getCurrentTurn().getPointsLeft())) {

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
            if (getCurrentLeg().getWinner() != -1) {
                if (getCurrentSet().getWinner() != -1) {
                    int sets = -1;
                    if (config.getType() == GameType.SETS) {
                        sets = getCurrentTurn().getSets() + 1;
                    }
                    int legs;
                    if (config.getType() == GameType.LEGS) {
                        legs = getCurrentTurn().getLegs() + 1;
                    } else {
                        legs = 0;
                    }

                    getCurrentTurn().setPointsLeft(0);
                    getCurrentTurn().setSets(sets);
                    getCurrentTurn().setLegs(legs);
                    if (getWinner() != null) {
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
                        turnIndex = (getCurrentSet().getStartIndex() + 1) % players.size();
                        createSet();
                        createLeg();
                    }
                } else {
                    // CONTINUE NEW LEG
                    for (int i = 0; i < players.size(); i++) {
                        Player player = players.get(i);
                        int legs = player.getLegs();

                        if (i == turnIndex) {
                            legs += 1;
                        }
                        player.setPointsLeft(config.getStartingPoints());
                        player.setDartsThrown(0);
                        player.setLegs(legs);
                    }
                    turnIndex = (getCurrentLeg().getStartIndex() + 1) % players.size();
                    createLeg();
                }
            } else {
                // CONTINUE
                turnIndex = (turnIndex + 1) % players.size();
            }
            getCurrentTurn().setNext(true);
            return true;
        }
        return false;
    }

    public void undoThrow() {
        if (sets.size() == 1 && sets.get(0).getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 0) {
            // NO THROW PERFORMED YET -> do nothing
            return;
        }

        getCurrentTurn().setNext(false);

        if (sets.size() == 1 && sets.get(0).getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 1) {
            // UNDO FIRST THROW OF GAME
            Throw last = getCurrentLeg().undoThrow();
            turnIndex = last.getPlayerIndex();
            getCurrentTurn().setLastThrow(-1);
            getCurrentTurn().setPointsLeft(config.getStartingPoints());
            getCurrentTurn().setDartsThrown(0);
            getCurrentTurn().setAverage("0.00");
            getCurrentTurn().setCheckoutPercentage("0.00");
        } else if (sets.size() >= 2 && getCurrentSet().getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 0) {
            // UNDO LAST THROW OF SET
            sets.remove(sets.size() - 1);
            Throw last = getCurrentLeg().undoThrow();
            turnIndex = last.getPlayerIndex();

            // restore player data
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);

                if (turnIndex == i) {
                    player.setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size() - players.size()).getPoints());
                    player.setAverage(getAverageCurrentTurn());
                    player.setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
                }

                player.setPointsLeft(getCurrentLeg().getPointsLeft()[i]);
                player.setDartsThrown(getCurrentLeg().getDartsThrown()[i]);

                int s = 0;
                int l = 0;

                for (Set set : sets) {
                    if (config.getType() == GameType.SETS) {
                        if (set.getWinner() == i) {
                            s += 1;
                        }
                    } else {
                        s = -1;
                    }
                }

                for (Leg leg : getCurrentSet().getLegs()) {
                    if (leg.getWinner() == i) {
                        l += 1;
                    }
                }

                player.setSets(s);
                player.setLegs(l);
            }
        } else if (getCurrentSet().getLegs().size() >= 2 && getCurrentLeg().getThrows().size() == 0) {
            // UNDO LAST THROW OF LEG
            getCurrentSet().getLegs().remove(getCurrentSet().getLegs().size() - 1);
            Throw last = getCurrentLeg().undoThrow();
            turnIndex = last.getPlayerIndex();

            // restore player data
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);

                if (turnIndex == i) {
                    player.setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size() - players.size()).getPoints());
                    player.setAverage(getAverageCurrentTurn());
                    player.setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
                }

                player.setPointsLeft(getCurrentLeg().getPointsLeft()[i]);
                player.setDartsThrown(getCurrentLeg().getDartsThrown()[i]);

                int l = 0;
                for (Leg leg : getCurrentSet().getLegs()) {
                    if (leg.getWinner() == i) {
                        l += 1;
                    }
                }

                player.setLegs(l);
            }
        } else {
            // UNDO STANDARD THROW
            Throw last = getCurrentLeg().undoThrow();
            turnIndex = last.getPlayerIndex();
            getCurrentTurn().setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size() - players.size()).getPoints());
            getCurrentTurn().setPointsLeft(getCurrentTurn().getPointsLeft() + last.getPoints());
            getCurrentTurn().setDartsThrown(getCurrentTurn().getDartsThrown() - last.getDartsThrown());
        }

        getCurrentTurn().setNext(true);
        getCurrentTurn().setAverage(getAverageCurrentTurn());
        getCurrentTurn().setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
    }

    public Player getWinner() {
        switch (config.getType()) {
            case LEGS:
                int legsNeededToWin;
                switch (config.getMode()) {
                    case FIRST_TO:
                        legsNeededToWin = config.getSize();
                        for (Player player : players) {
                            if (player.getLegs() == legsNeededToWin) {
                                return player;
                            }
                        }
                        break;
                    case BEST_OF:
                        legsNeededToWin = Math.round(config.getSize() / 2) + 1;
                        for (Player player : players) {
                            if (player.getLegs() == legsNeededToWin) {
                                return player;
                            }
                        }
                        break;
                }
                break;
            case SETS:
                int setsNeededToWin;
                switch (config.getMode()) {
                    case FIRST_TO:
                        setsNeededToWin = config.getSize();
                        for (Player player : players) {
                            if (player.getSets() == setsNeededToWin) {
                                return player;
                            }
                        }
                        break;
                    case BEST_OF:
                        setsNeededToWin = Math.round(config.getSize() / 2) + 1;
                        for (Player player : players) {
                            if (player.getSets() == setsNeededToWin) {
                                return player;
                            }
                        }
                        break;
                }
                break;
        }
        return null;
    }

    public GameSnapshot getSnapshot() {
        return new GameSnapshot(getStatusAsString(), getDescription(), players.stream().map(player -> player.getSnapshot()).collect(Collectors.toList()));
    }

    public String getDescription() {
        return config.getModeAsString() + " " + config.getSize() + " " + config.getTypeAsString();
    }

    public void setConfig(GameConfig config) {
        this.config = config;
    }


    private Set getCurrentSet() {
        return sets.get(sets.size() - 1);
    }

    private Leg getCurrentLeg() {
        ArrayList<Leg> legs = getCurrentSet().getLegs();
        return legs.get(legs.size() - 1);
    }

    private Player getCurrentTurn() {
        return players.get(turnIndex);
    }

    private String getAverageCurrentTurn() {
        int totalDartsThrown = 0;
        int totalPointsScored = 0;
        for (Set set : sets) {
            for (Leg leg : set.getLegs()) {
                totalDartsThrown += leg.getDartsThrown()[turnIndex];
                totalPointsScored += (config.getStartingPoints() - leg.getPointsLeft()[turnIndex]);
            }
        }
        if (totalDartsThrown == 0) {
            return "0.00";
        }

        double avg = ((3 * totalPointsScored) / (double) totalDartsThrown);
        String rawString = String.format("%f", avg);
        return rawString.substring(0, rawString.indexOf(",") + 3).replace(",", ".");
    }

    private String getCheckoutPercentageCurrentTurn() {
        int totalLegsWon = 0;
        int totalDartsOnDouble = 0;
        for (Set set : sets) {
            for (Leg leg : set.getLegs()) {
                if (leg.getWinner() == turnIndex) {
                    totalLegsWon++;
                }
                totalDartsOnDouble += leg.getDartsOnDouble()[turnIndex];
            }
        }

        if (totalDartsOnDouble == 0) {
            return "0.00";
        }

        double checkoutPercentage = (totalLegsWon / (double) totalDartsOnDouble) * 100;
        String rawString = String.format("%f", checkoutPercentage);
        return rawString.substring(0, rawString.indexOf(",") + 3).replace(",", ".");

    }

    private void createSet() {
        if (config.getMode() == GameMode.FIRST_TO) {
            if (config.getType() == GameType.LEGS) {
                sets.add(new Set(turnIndex, config.getSize()));
            } else {
                sets.add(new Set(turnIndex, 3));
            }
        } else {
            if (config.getType() == GameType.LEGS) {
                sets.add(new Set(turnIndex, Math.round(config.getSize() / 2) + 1));
            } else {
                sets.add(new Set(turnIndex, 3));
            }
        }
    }

    private void createLeg() {
        getCurrentSet().getLegs().add(new Leg(turnIndex, players.size(), config.getStartingPoints()));
    }

    private void initPlayers() {
        int index = 1;
        for (Player player : players) {
            if (player.getName().equals("")) {
                player.setName("Player " + index);
                index++;
            }
            player.setNext(false);
            player.setLastThrow(-1);
            player.setPointsLeft(config.getStartingPoints());
            player.setDartsThrown(0);
            if (config.getType() == GameType.SETS) {
                player.setSets(0);
            } else {
                player.setSets(-1);
            }
            player.setLegs(0);
            player.setAverage("0.00");
            player.setCheckoutPercentage("0.00");
        }
        players.get(turnIndex).setNext(true);
    }

    private String getStatusAsString() {
        switch (status) {
            case PENDING:
                return "pending";
            case RUNNING:
                return "running";
            case FINISHED:
                return "finished";
        }
        return null;
    }

}