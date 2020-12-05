package dartServer.gameengine;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.listeners.*;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.User;
import dartServer.networking.Client;
import dartServer.networking.NetworkManager;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Control class for the server-side game logic and infrastructure. Games take place in lobbies.
 * users with their SocketAdress are managed via mapping.
 */
public class GameEngine {

    private static final HashMap<SocketAddress, User> users = new HashMap<>();
    private static final List<Lobby> lobbies = new ArrayList<>();
    private static boolean eventsRegistered;

    private GameEngine() {
        throw new IllegalStateException("Can not be instantiated!");
    }

    /**
     * Initialize the GameEngine, resets a previously initialized GameEngine.
     */
    public static void init() {
        lobbies.clear();
        users.clear();
        registerEvents();
    }

    /**
     * Register all events for the GameEngine.
     */
    private static void registerEvents() {
        if (eventsRegistered)
            return;
        NetworkManager.registerListener(new ClientConnectListener());
        NetworkManager.registerListener(new ClientDisconnectListener());
        NetworkManager.registerListener(new AuthenticationListener());
        NetworkManager.registerListener(new ServerListener());
        NetworkManager.registerListener(new GameListener());
        eventsRegistered = true;
    }

    public static User createUser(String uid, String username, Client client) {
        User user = new User(uid, username, client);
        if (addUser(user)) {
            return user;
        }
        return null;
    }

    public static Lobby createLobby(User user) {
        if (user.getLobbyId() == null) {
            Lobby lobby = new Lobby(user);
            user.setLobbyId(lobby.getId());
            lobbies.add(lobby);
            return lobby;
        }
        return null;
    }

    public static Lobby joinLobby(User user, int code) {
        if (user.getLobbyId() == null) {
            Lobby lobby = getLobbyByCode(code);
            if (lobby != null) {
                if (lobby.addUser(user)) {
                    user.setLobbyId(lobby.getId());
                    return lobby;
                }
            }
        }
        return null;
    }







    public static User getUser(SocketAddress address) {
        return users.get(address);
    }

    public static Lobby getLobbyByUser(User user) {
        Integer lobbyId = user.getLobbyId();
        if (lobbyId == null) return null;

        for (Lobby lobby : lobbies) {
            if (lobby.getId() == lobbyId)
                return lobby;
        }
        return null;
    }

    public static User[] getUsers() {
        return users.values().toArray(new User[0]);
    }


    private static boolean addUser(User user) {
        SocketAddress address = user.getClient().getAddress();
        if (!users.containsKey(address)) {
            users.put(address, user);
            return true;
        }

        return true;
    }

    private static Lobby getLobbyByCode(int code) {
        for (Lobby l : lobbies) {
            if (l.getCode() == code)
                return l;
        }
        return null;
    }


    public static void broadcastToLobby(Lobby lobby, ResponsePacket packet) {
        Arrays.stream(lobby.getUsers()).forEach(player -> player.sendMessage(packet));
    }

    public static void updateUserAddress(SocketAddress address, User user) {
        users.remove(address);
        addUser(user);
    }

    public static void removeUser(User user) {
        SocketAddress address = user.getClient().getAddress();
        users.remove(address);
        if (user.getLobbyId() != null) {
            getLobbyByUser(user).removeUser(user);
        }
    }

    public static User getUserByName(String name) {
        return Arrays.stream(getUsers()).filter(user -> user.getUsername().equals(name)).findFirst().orElse(null);
    }

    public static void removeLobby(Lobby lobby) {
        lobbies.remove(lobby);
    }

    public static List<Lobby> getLobbies() {
        return lobbies;
    }
}
