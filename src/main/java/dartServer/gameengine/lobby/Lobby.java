package dartServer.gameengine.lobby;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.model.Game;
import dartServer.gameengine.model.GameConfig;
import dartServer.gameengine.model.Player;
import dartServer.gameengine.model.Throw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Lobby {

    private static int lobbyCount = 1;

    private final int id;

    private final ArrayList<User> users;

    private final Game game;


    // --== Constructor ==--

    public Lobby(User user) {
        users = new ArrayList<>();
        users.add(user);
        Player player = new Player(user.getUsername());
        user.setPlayer(player);
        game = new Game(player);
        id = lobbyCount++;
    }

    public User getOwner() {
        return users.get(0);
    }


    // --== Methods ==--

    public boolean addUser(User user) {
        Player player = new Player(user.getUsername());
        boolean added = game.addPlayer(player);
        if (added) {
            user.setPlayer(player);
            users.add(user);
        }

        return added;
    }

    public void removeUser(User user) {
        users.remove(user);
        Player player = user.getPlayer();
        game.removePlayer(player);
        user.setPlayer(null);
    }

    public boolean updateGameConfig(User user, GameConfig gameConfig) {
        Player player = user.getPlayer();
        return game.updateGameConfig(player, gameConfig);
    }

    public boolean startGame(User user) {
        Player player = user.getPlayer();
        return game.start(player);
    }

    public boolean performThrow(User user, Throw t) {
        Player player = user.getPlayer();
        return game.performThrow(player, t);
    }

    public boolean undoThrow(User user) {
        Player player = user.getPlayer();
        return game.undoThrow(player);
    }

    public void cancelGame() {
        game.cancel();
    }

    /**
     * broadcasts the given packet to all Clients in the Lobby.
     *
     * @param packet the packet that needs to be sent
     */
    public void broadcastToUsers(ResponsePacket packet) {
        for (User user : users) {
            if (user.isConnected()) {
                user.sendMessage(packet);
            }
        }
    }


    // --== Getter/Setter ==--

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public User[] getUsers() {
        return Arrays.stream(GameEngine.getUsers()).filter(user -> user.getLobbyId() == id).toArray(User[]::new);
    }

    public int getCode() {
        return id + 999;
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "id=" + id + ", " +
                "code=" + getCode() + ", " +
                "users=" + users.stream().map(user -> user.getUsername()).collect(Collectors.toList()) +
                '}';
    }

}
