package dartServer.gameengine;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.listeners.ClientConnectListener;
import dartServer.gameengine.listeners.ClientDisconnectListener;
import dartServer.gameengine.listeners.UserJoinListener;
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
     *
     * @param config The match config for all games managed by the game engine.
     */
    public static void init() {
        lobbies.clear();
        users.clear();

        addLobby(new Lobby());

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
        NetworkManager.registerListener(new UserJoinListener());
        NetworkManager.registerListener(new TeamConfigListener());
        NetworkManager.registerListener(new TeamFormationListener());
        NetworkManager.registerListener(new DeltaPacketListener());
        NetworkManager.registerListener(new DebugMessageListener());
        NetworkManager.registerListener(new ReplayRequestListener());
        NetworkManager.registerListener(new PauseHandlingListener());
        eventsRegistered = true;
    }

    public static void broadcastToLobby(String name, ResponsePacket packet) {
        Lobby lobby = getLobbyByName(name);
        if (lobby == null)
            throw new IllegalArgumentException("Name must correspond to a lobby.");
        broadcastToLobby(lobby, packet);
    }

    public static void broadcastToLobby(Lobby lobby, ResponsePacket packet) {
        Arrays.stream(lobby.getUsers()).forEach(user -> user.sendMessage(packet));
    }

    public static void addUser(SocketAddress address, User user) {
        users.put(address, user);
        getLobbyByName(user.getLobbyName()).getActiveGame().addUser(user);
    }

    public static void updateUserAddress(SocketAddress address, User user) {
        users.remove(user);
        addUser(address, user);
    }

    public static void removeUser(SocketAddress address) {
        User user = getUser(address);
        users.remove(address);
        getLobbyByName(user.getLobbyName()).getActiveGame().removeUser(user);
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
        if (getLobbyByName(l.getName()) != null)
            throw new IllegalArgumentException("Lobby name must be unique!");
        lobbies.add(l);
    }

    public static Lobby getLobbyByName(String lobbyName) {
        for (Lobby l : lobbies) {
            if (l.getName().equals(lobbyName))
                return l;
        }
        return null;
    }

    public static boolean isLobby(String lobbyName) {
        return getLobbyByName(lobbyName) != null;
    }
}
