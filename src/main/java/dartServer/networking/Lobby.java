package dartServer.networking;

import dartServer.GameLoop;
import dartServer.model.Game;

import java.util.ArrayList;

public class Lobby {

    private ArrayList<User> users;

    private GameLoop gameLoop;
    private Game game;

    public Lobby(User user) {
        users = new ArrayList<>();
        users.add(user);
    }


}
