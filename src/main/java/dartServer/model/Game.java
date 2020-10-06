package dartServer.model;

import dartServer.networking.User;
import dartServer.networking.artefacts.responses.Snapshot;

import java.util.ArrayList;

public class Game {

    private GameConfig config;
    private GameStatus status;

    private ArrayList<User> users;
    private ArrayList<User> sets;

    private int turnIndex;

    private GameLoop gameLoop;

    public Game(User user) {
        this.config = new GameConfig();
        this.status = GameStatus.PENDING;
        users = new ArrayList<>();
        sets = new ArrayList<>();
        turnIndex = 0;
        users.add(user);

        gameLoop = new GameLoop(this);
    }

    public void start() {
        if(users.size() > 1) {
            gameLoop.run();
        }
    }

    public Snapshot getSnapshot() {
        switch (status) {
            case PENDING:
                return new Snapshot();
            case RUNNING:
                return new Snapshot();
        }
        return null;
    }

    public User getWinner() {
        return null;
        // TODO
    }

    public boolean addUser(User user) {
        if(users.size() < 4) {
            users.add(user);
            return true;
        }
        return false;
    }

    public boolean hasUser(User user) {
        for(User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return true;
            }
        }

        return false;
    }

    public boolean hasUser(String username) {
        for(User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }
}
