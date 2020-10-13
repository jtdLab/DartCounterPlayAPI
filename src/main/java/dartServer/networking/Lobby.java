package dartServer.networking;

import dartServer.model.Game;
import dartServer.model.Player;
import dartServer.model.Throw;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Lobby {

    private UUID id;

    private CopyOnWriteArrayList<User> users;

    private Game game;

    public Lobby(User user) {
        id = UUID.randomUUID();
        users = new CopyOnWriteArrayList<>();
        game = new Game(new Player(user.getUsername()));
        join(user);
    }


    protected boolean join(User user) {
        if(users.size() < 4) {
            users.add(user);
            game.addPlayer(new Player(user.getUsername()));
            return true;
        }
        return false;
    }

    protected void leave(User user) {
        int index = users.indexOf(user);
        users.remove(index);
        game.removePlayer(index);
    }

    protected boolean start() {
        if(users.size() > 1) {
            game.start();
            return true;
        }
        return false;
    }

    protected boolean doThrow(Throw t, User user) {
        t.setPlayerIndex(users.indexOf(user));
        return game.performThrow(t);
    }

    protected boolean undoThrow(User user) {
        int index = users.indexOf(user);
        if((game.getTurnIndex() +1) % users.size() == index) {
            game.undoThrow();
            return true;
        }
        return false;
    }

    public User getOwner() {
        return users.get(0);
    }

    protected CopyOnWriteArrayList<User> getUsers() {
        return users;
    }

    protected Game getGame() {
        return game;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lobby lobby = (Lobby) o;
        return this.id.equals(lobby.id);
    }

}
