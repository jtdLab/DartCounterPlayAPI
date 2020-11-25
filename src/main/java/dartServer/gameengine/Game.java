package dartServer.gameengine;

import dartServer.commons.artifacts.GameSnapshot;
import dartServer.gameengine.lobby.Player;
import dartServer.gameengine.model.GameConfig;
import dartServer.gameengine.model.Leg;
import dartServer.gameengine.model.Set;
import dartServer.gameengine.model.Throw;
import dartServer.gameengine.model.enums.GameMode;
import dartServer.gameengine.model.enums.GameStatus;
import dartServer.gameengine.model.enums.GameType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game {

    private GameStatus status;
    private GameConfig config;

    private final ArrayList<Player> players;
    private final ArrayList<Set> sets;

    private int turnIndex;


    // --== Constructor ==--

    public Game(Player player) {
        this.config = new GameConfig();
        this.status = GameStatus.PENDING;
        players = new ArrayList<>();
        sets = new ArrayList<>();
        turnIndex = 0;
        players.add(player);
    }


    // --== Methods ==--

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

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean start(Player player) {
        Player owner = getOwner();

        if (owner.equals(player) && status == GameStatus.PENDING && players.size() > 1) {
            createSet();
            createLeg();
            initPlayers();
            status = GameStatus.RUNNING;
            return true;
        }
        return false;
    }

    public boolean performThrow(Player player, Throw t)  {
        Player currentTurn = getCurrentTurn();
        // sets the Player who threw

        Integer index = indexOf(player);
        if (index == null) {
            return false;
        }

        t.setPlayerIndex(index);


        if (status == GameStatus.RUNNING && currentTurn.equals(player)) {
            if (turnIndex == t.getPlayerIndex() && ThrowValidator.isValidThrow(t, getCurrentTurn().getPointsLeft())) {

                getCurrentTurn().setNext(false);

                // updates the leg data
                getCurrentLeg().performThrow(t);

                // updates the Player data
                getCurrentTurn().setLastThrow(t.getPoints());
                getCurrentTurn().setPointsLeft(getCurrentTurn().getPointsLeft() - t.getPoints());
                getCurrentTurn().setDartsThrown(getCurrentTurn().getDartsThrown() + t.getDartsThrown());
                getCurrentTurn().setAverage(getAverageCurrentTurn());
                getCurrentTurn().setCheckoutPercentage(getCheckoutPercentageCurrentTurn());

                // updates the reference to the Player who has next turn
                // updates the Player data and creates next leg and set when needed
                if (getCurrentLeg().getWinner() != null) {
                    if (getCurrentSet().getWinner() != null) {
                        Integer sets = null;

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
                                Player p = players.get(i);
                                p.setPointsLeft(config.getStartingPoints());
                                p.setDartsThrown(0);
                                p.setLegs(0);
                            }
                            turnIndex = (getCurrentSet().getStartIndex() + 1) % players.size();
                            createSet();
                            createLeg();
                        }
                    } else {
                        // CONTINUE NEW LEG
                        for (int i = 0; i < players.size(); i++) {
                            Player p = players.get(i);
                            int legs = p.getLegs();

                            if (i == turnIndex) {
                                legs += 1;
                            }
                            p.setPointsLeft(config.getStartingPoints());
                            p.setDartsThrown(0);
                            p.setLegs(legs);
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
        }
        return false;
    }

    public boolean undoThrow(Player player) {
        Player previous = getPreviousTurn();

        if (status == GameStatus.RUNNING && previous.equals(player)) {
            if (sets.size() == 1 && sets.get(0).getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 0) {
                // NO THROW PERFORMED YET -> do nothing
                return false;
            }

            getCurrentTurn().setNext(false);

            if (sets.size() == 1 && sets.get(0).getLegs().size() == 1 && getCurrentLeg().getThrows().size() <= players.size()) {
                // UNDO FIRST THROW OF GAME OF PLAYER
                Throw last = getCurrentLeg().undoThrow();
                turnIndex = last.getPlayerIndex();
                getCurrentTurn().setLastThrow(null);
                getCurrentTurn().setPointsLeft(config.getStartingPoints());
                getCurrentTurn().setDartsThrown(0);
                getCurrentTurn().setAverage("0.00");
                getCurrentTurn().setCheckoutPercentage("0.00");
            } else if (sets.size() >= 2 && getCurrentSet().getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 0) {
                // UNDO LAST THROW OF SET
                sets.remove(sets.size() - 1);
                Throw last = getCurrentLeg().undoThrow();
                turnIndex = last.getPlayerIndex();

                // restore Player data
                for (int i = 0; i < players.size(); i++) {
                    Player p = players.get(i);

                    if (turnIndex == i) {
                        p.setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size() - players.size()).getPoints());
                        p.setAverage(getAverageCurrentTurn());
                        p.setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
                    }

                    p.setPointsLeft(getCurrentLeg().getPointsLeft()[i]);
                    p.setDartsThrown(getCurrentLeg().getDartsThrown()[i]);

                    Integer s = 0;
                    int l = 0;

                    for (Set set : sets) {
                        if (config.getType() == GameType.SETS) {
                            if (set.getWinner() != null && set.getWinner() == i) {
                                s += 1;
                            }
                        } else {
                            s = null;
                        }
                    }

                    for (Leg leg : getCurrentSet().getLegs()) {
                        if (leg.getWinner() != null && leg.getWinner() == i) {
                            l += 1;
                        }
                    }

                    p.setSets(s);
                    p.setLegs(l);
                }
            } else if (getCurrentSet().getLegs().size() >= 2 && getCurrentLeg().getThrows().size() == 0) {
                // UNDO LAST THROW OF LEG
                getCurrentSet().getLegs().remove(getCurrentSet().getLegs().size() - 1);
                Throw last = getCurrentLeg().undoThrow();
                turnIndex = last.getPlayerIndex();

                // restore Player data
                for (int i = 0; i < players.size(); i++) {
                    Player p = players.get(i);

                    if (turnIndex == i) {
                        p.setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size() - players.size()).getPoints());
                        p.setAverage(getAverageCurrentTurn());
                        p.setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
                    }

                    p.setPointsLeft(getCurrentLeg().getPointsLeft()[i]);
                    p.setDartsThrown(getCurrentLeg().getDartsThrown()[i]);

                    int l = 0;
                    for (Leg leg : getCurrentSet().getLegs()) {
                        if (leg.getWinner() != null && leg.getWinner() == i) {
                            l += 1;
                        }
                    }

                    p.setLegs(l);
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
            return true;
        }
        return false;
    }

    public boolean updateConfig(Player player, GameConfig config) {
        if (player.equals(getOwner())) {
            this.config = config;
            return true;
        }
        return false;
    }

    public GameSnapshot getSnapshot() {
        return new GameSnapshot(status, getDescription(), players.stream().map(player -> player.getSnapshot()).collect(Collectors.toList()));
    }

    public ArrayList<Player> getPlayers() {
        return players;
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

    private String getDescription() {
        return config.getModeAsString() + " " + config.getSize() + " " + config.getTypeAsString();
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

    private Player getPreviousTurn() {
        int i = Math.floorMod(turnIndex - 1, players.size());
        return players.get(i);
    }

    private Player getOwner() {
        return players.get(0);
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
                if (leg.getWinner() != null && leg.getWinner() == turnIndex) {
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
        for (Player player : players) {
            player.setNext(false);
            player.setLastThrow(null);
            player.setPointsLeft(config.getStartingPoints());
            player.setDartsThrown(0);
            if (config.getType() == GameType.SETS) {
                player.setSets(0);
            } else {
                player.setSets(null);
            }
            player.setLegs(0);
            player.setAverage("0.00");
            player.setCheckoutPercentage("0.00");
            player.setPlaying(true);
        }
        players.get(turnIndex).setNext(true);
    }

    private Integer indexOf(Player player) {
        for (int i = 0; i < players.size(); i++) {
            if (player.equals(players.get(i))) {
                return i;
            }
        }

        return null;
    }

}