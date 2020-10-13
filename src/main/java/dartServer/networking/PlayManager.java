package dartServer.networking;

import dartServer.model.Game;
import dartServer.model.Player;
import dartServer.model.Throw;
import dartServer.networking.Lobby;
import dartServer.networking.User;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PlayManager {

    private final ExecutorService service = Executors.newCachedThreadPool();
    private final CopyOnWriteArrayList<Lobby> lobbies = new CopyOnWriteArrayList();

    public PlayManager() {

    }

    public void create(User user) {
        lobbies.add(new Lobby(user));
    }

    public void cancel(User user, Lobby lobby) {
       if(user.equals(lobby.getOwner())) {
           lobbies.remove(lobby);
       }
    }

    public boolean join(User user, Lobby lobby) {
        return lobby.join(user);
    }

    public void leave(User user, Lobby lobby) {
        lobby.leave(user);
    }

    public boolean start(User user, Lobby lobby) {
        if(lobby.getOwner().equals(user)) {
            return lobby.start();
        }
        return false;
    }

    public boolean doThrow(Throw t, User user, Lobby lobby) {
        return lobby.doThrow(t, user);
    }

    public boolean undoThrow(User user, Lobby lobby) {
        return lobby.undoThrow(user);
    }



    public Lobby getLobby(String username) {
        for (Lobby lobby : lobbies) {
            for(User user : lobby.getUsers()) {
                if(username.equals(user.getUsername())) {
                    return lobby;
                }
            }
        }
        return null;
    }

    public Lobby getLobby(User user) {
        for (Lobby lobby : lobbies) {
            if(lobby.getUsers().contains(user)) {
                return lobby;
            }
        }
        return null;
    }

    public Game getGame(String username) {
        for (Lobby lobby : lobbies) {
            for(User user : lobby.getUsers()) {
                if(username.equals(user.getUsername())) {
                    return lobby.getGame();
                }
            }
        }
        return null;
    }

    public Game getGame(User user) {
        for (Lobby lobby : lobbies) {
            if(lobby.getUsers().contains(user)) {
                return lobby.getGame();
            }
        }
        return null;
    }

    public User getUser(String username) {
        for (Lobby lobby : lobbies) {
            for(User user : lobby.getUsers()) {
                if(username.equals(user.getUsername())) {
                    return user;
                }
            }
        }
        return null;
    }

}
