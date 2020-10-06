package dartServer;

import dartServer.model.Game;
import dartServer.networking.User;

import java.util.concurrent.CopyOnWriteArrayList;


public class GameManager {

    private final CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<Game> games = new CopyOnWriteArrayList();

    public GameManager() {

    }

    public void createGame(User user) {
        Game game = new Game(user);
        games.add(game);
    }



    public Game getGame(String username) {
        for(Game game : games) {
            if(game.hasUser(username)) {
                return game;
            }
        }
        return null;
    }

    public Game getGame(User user) {
        for(Game game : games) {
            if(game.hasUser(user)) {
                return game;
            }
        }
        return null;
    }

    public User getUser(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean isPlaying(User user) {
        return getGame(user) != null;
    }
}
