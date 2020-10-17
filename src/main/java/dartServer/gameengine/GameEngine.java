package dartServer.gameengine;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.listeners.*;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.User;
import dartServer.networking.NetworkManager;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Control class for the server-side game logic and infrastructure. Games take place in lobbies.
 * Users with their SocketAdress are managed via mapping.
 */
public class GameEngine {

    private static HashMap<SocketAddress, User> users = new HashMap<>();
    private static List<Lobby> lobbies = new ArrayList<Lobby>();
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
        if(eventsRegistered)
            return;
        NetworkManager.registerListener(new ClientConnectListener());
        NetworkManager.registerListener(new ClientDisconnectListener());
        NetworkManager.registerListener(new AuthenticationListener());
        NetworkManager.registerListener(new ServerListener());
        NetworkManager.registerListener(new GameListener());
        eventsRegistered = true;
    }

    public static void broadcastToLobby(long id, ResponsePacket packet) {
        Lobby lobby = getLobbyById(id);
        if (lobby == null)
            throw new IllegalArgumentException("Name must correspond to a lobby.");
        broadcastToLobby(lobby, packet);
    }

    public static void broadcastToLobby(Lobby lobby, ResponsePacket packet) {
        Arrays.stream(lobby.getUsers()).forEach(user -> user.sendMessage(packet));
    }

    public static void addUser(SocketAddress address, User user) {
        users.put(address, user);
        getLobbyById(user.getLobbyId()).getActiveGame().addUser(user);
    }

    public static void updateUserAddress(SocketAddress address, User user) {
        users.remove(user);
        addUser(address, user);
    }

    public static void removeUser(SocketAddress address) {
        User user = getUser(address);
        users.remove(address);
        getLobbyById(user.getLobbyId()).getActiveGame().removeUser(user);
    }

    public static User getUser(SocketAddress address) {
        return users.get(address);
    }

    public static User getUserByName(String userName) {
        return Arrays.stream(getUsers()).filter(user -> user.getName().equals(userName)).findFirst().orElse(null);
    }

    public static User[] getUsers() {
        return users.values().toArray(new User[0]);
    }


    public static void addLobby(Lobby l) {
        if (getLobbyById(l.getLobbyId()) != null)
            throw new IllegalArgumentException("Lobby name must be unique!");
        lobbies.add(l);
    }

    public static Lobby getLobbyById(long id) {
        for (Lobby l : lobbies) {
            if (l.getLobbyId() == id)
                return l;
        }
        return null;
    }

    public static boolean isLobby(long id) {
        return getLobbyById(id) != null;
    }
}
