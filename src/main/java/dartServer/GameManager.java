package dartServer;

import dartServer.model.Game;
import dartServer.model.Player;
import dartServer.networking.Lobby;
import dartServer.networking.User;
import dartServer.networking.artefacts.responses.PlayerSnapshot;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameManager {

    private final ExecutorService service = Executors.newCachedThreadPool();

    private final CopyOnWriteArrayList<Lobby> lobbies = new CopyOnWriteArrayList();

    public GameManager() {

    }

    public void createGame(User user) {
        lobbies.add(new Lobby(user));
    }

    public void joinGame(Player player, Game game) {
        game.addPlayer(player);
    }

    public void startGame(Game game) {
        game.start();
    }

    public Game getLobby(String username) {
        for(Lobby lobby : lobbies) {

        }
        return null;
    }

   /* public User getUser(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }*/

}
