package dartServer.gameengine;

import dartServer.commons.artifacts.GameSnapshot;
import dartServer.gameengine.lobby.User;
import dartServer.gameengine.model.*;
import dartServer.gameengine.model.enums.GameMode;
import dartServer.gameengine.model.enums.GameStatus;
import dartServer.gameengine.model.enums.GameType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game {

    private GameStatus status;
    private GameConfig config;

    private ArrayList<User> users;
    private ArrayList<Set> sets;

    private int turnIndex;

    public Game(User user) {
        this.config = new GameConfig();
        this.status = GameStatus.PENDING;
        users = new ArrayList<>();
        sets = new ArrayList<>();
        turnIndex = 0;
        users.add(user);
    }


    public boolean addUser(User user) {
        if (users.size() < 4) {
            for (User u : users) {
                if (u.getName().equals(user.getName())) return false;
            }
            users.add(user);
            return true;
        }
        return false;
    }

    public void removeUser(int index) {
        users.remove(index);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public boolean start() {
        if(status == GameStatus.PENDING && users.size() > 2) {
            createSet();
            createLeg();
            initusers();
            status = GameStatus.RUNNING;
            return true;
        }
        return false;
    }

    public boolean performThrow(Throw t) {
        if (status == GameStatus.RUNNING) {
            if (turnIndex == t.getUserIndex() && ThrowValidator.isValidThrow(t, getCurrentTurn().getPointsLeft())) {

                getCurrentTurn().setNext(false);

                // sets the User who threw
                t.setUserIndex(turnIndex);

                // updates the leg data
                getCurrentLeg().performThrow(t);

                // updates the User data
                getCurrentTurn().setLastThrow(t.getPoints());
                getCurrentTurn().setPointsLeft(getCurrentTurn().getPointsLeft() - t.getPoints());
                getCurrentTurn().setDartsThrown(getCurrentTurn().getDartsThrown() + t.getDartsThrown());
                getCurrentTurn().setAverage(getAverageCurrentTurn());
                getCurrentTurn().setCheckoutPercentage(getCheckoutPercentageCurrentTurn());

                // updates the reference to the User who has next turn
                // updates the User data and creates next leg and set when needed
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
                            for (int i = 0; i < users.size(); i++) {
                                User user = users.get(i);
                                user.setPointsLeft(config.getStartingPoints());
                                user.setDartsThrown(0);
                                user.setLegs(0);
                            }
                            turnIndex = (getCurrentSet().getStartIndex() + 1) % users.size();
                            createSet();
                            createLeg();
                        }
                    } else {
                        // CONTINUE NEW LEG
                        for (int i = 0; i < users.size(); i++) {
                            User user = users.get(i);
                            int legs = user.getLegs();

                            if (i == turnIndex) {
                                legs += 1;
                            }
                            user.setPointsLeft(config.getStartingPoints());
                            user.setDartsThrown(0);
                            user.setLegs(legs);
                        }
                        turnIndex = (getCurrentLeg().getStartIndex() + 1) % users.size();
                        createLeg();
                    }
                } else {
                    // CONTINUE
                    turnIndex = (turnIndex + 1) % users.size();
                }
                getCurrentTurn().setNext(true);
                return true;
            }
        }
        return false;
    }

    public void undoThrow() {
        if (status == GameStatus.RUNNING) {
            if (sets.size() == 1 && sets.get(0).getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 0) {
                // NO THROW PERFORMED YET -> do nothing
                return;
            }

            getCurrentTurn().setNext(false);

            if (sets.size() == 1 && sets.get(0).getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 1) {
                // UNDO FIRST THROW OF GAME
                Throw last = getCurrentLeg().undoThrow();
                turnIndex = last.getUserIndex();
                getCurrentTurn().setLastThrow(-1);
                getCurrentTurn().setPointsLeft(config.getStartingPoints());
                getCurrentTurn().setDartsThrown(0);
                getCurrentTurn().setAverage("0.00");
                getCurrentTurn().setCheckoutPercentage("0.00");
            } else if (sets.size() >= 2 && getCurrentSet().getLegs().size() == 1 && getCurrentLeg().getThrows().size() == 0) {
                // UNDO LAST THROW OF SET
                sets.remove(sets.size() - 1);
                Throw last = getCurrentLeg().undoThrow();
                turnIndex = last.getUserIndex();

                // restore User data
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);

                    if (turnIndex == i) {
                        user.setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size() - users.size()).getPoints());
                        user.setAverage(getAverageCurrentTurn());
                        user.setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
                    }

                    user.setPointsLeft(getCurrentLeg().getPointsLeft()[i]);
                    user.setDartsThrown(getCurrentLeg().getDartsThrown()[i]);

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

                    user.setSets(s);
                    user.setLegs(l);
                }
            } else if (getCurrentSet().getLegs().size() >= 2 && getCurrentLeg().getThrows().size() == 0) {
                // UNDO LAST THROW OF LEG
                getCurrentSet().getLegs().remove(getCurrentSet().getLegs().size() - 1);
                Throw last = getCurrentLeg().undoThrow();
                turnIndex = last.getUserIndex();

                // restore user data
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);

                    if (turnIndex == i) {
                        user.setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size() - users.size()).getPoints());
                        user.setAverage(getAverageCurrentTurn());
                        user.setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
                    }

                    user.setPointsLeft(getCurrentLeg().getPointsLeft()[i]);
                    user.setDartsThrown(getCurrentLeg().getDartsThrown()[i]);

                    int l = 0;
                    for (Leg leg : getCurrentSet().getLegs()) {
                        if (leg.getWinner() == i) {
                            l += 1;
                        }
                    }

                    user.setLegs(l);
                }
            } else {
                // UNDO STANDARD THROW
                Throw last = getCurrentLeg().undoThrow();
                turnIndex = last.getUserIndex();
                getCurrentTurn().setLastThrow(getCurrentLeg().getThrows().get(getCurrentLeg().getThrows().size() - users.size()).getPoints());
                getCurrentTurn().setPointsLeft(getCurrentTurn().getPointsLeft() + last.getPoints());
                getCurrentTurn().setDartsThrown(getCurrentTurn().getDartsThrown() - last.getDartsThrown());
            }

            getCurrentTurn().setNext(true);
            getCurrentTurn().setAverage(getAverageCurrentTurn());
            getCurrentTurn().setCheckoutPercentage(getCheckoutPercentageCurrentTurn());
        }
    }

    public User getWinner() {
        switch (config.getType()) {
            case LEGS:
                int legsNeededToWin;
                switch (config.getMode()) {
                    case FIRST_TO:
                        legsNeededToWin = config.getSize();
                        for (User user : users) {
                            if (user.getLegs() == legsNeededToWin) {
                                return user;
                            }
                        }
                        break;
                    case BEST_OF:
                        legsNeededToWin = Math.round(config.getSize() / 2) + 1;
                        for (User user : users) {
                            if (user.getLegs() == legsNeededToWin) {
                                return user;
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
                        for (User user : users) {
                            if (user.getSets() == setsNeededToWin) {
                                return user;
                            }
                        }
                        break;
                    case BEST_OF:
                        setsNeededToWin = Math.round(config.getSize() / 2) + 1;
                        for (User user : users) {
                            if (user.getSets() == setsNeededToWin) {
                                return user;
                            }
                        }
                        break;
                }
                break;
        }
        return null;
    }

    public GameSnapshot getSnapshot() {
        return new GameSnapshot(getStatusAsString(), getDescription(), users.stream().map(user -> user.getSnapshot()).collect(Collectors.toList()));
    }

    public String getDescription() {
        return config.getModeAsString() + " " + config.getSize() + " " + config.getTypeAsString();
    }

    public void setConfig(GameConfig config) {
        this.config = config;
    }

    public int getTurnIndex() {
        return turnIndex;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    private Set getCurrentSet() {
        return sets.get(sets.size() - 1);
    }

    private Leg getCurrentLeg() {
        ArrayList<Leg> legs = getCurrentSet().getLegs();
        return legs.get(legs.size() - 1);
    }

    private User getCurrentTurn() {
        return users.get(turnIndex);
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
        getCurrentSet().getLegs().add(new Leg(turnIndex, users.size(), config.getStartingPoints()));
    }

    private void initusers() {
        int index = 1;
        for (User user : users) {
            if (user.getName().equals("")) {
                user.setName("User " + index);
                index++;
            }
            user.setNext(false);
            user.setLastThrow(-1);
            user.setPointsLeft(config.getStartingPoints());
            user.setDartsThrown(0);
            if (config.getType() == GameType.SETS) {
                user.setSets(0);
            } else {
                user.setSets(-1);
            }
            user.setLegs(0);
            user.setAverage("0.00");
            user.setCheckoutPercentage("0.00");
        }
        users.get(turnIndex).setNext(true);
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