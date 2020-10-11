package dartServer.networking;

import dartServer.GameLoop;
import dartServer.model.Game;

import java.util.concurrent.CopyOnWriteArrayList;

public class Lobby {

    private final CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();

    private GameLoop gameLoop;
    private Game game;

    public Lobby(User user) {
        users.add(user);
    }


}
