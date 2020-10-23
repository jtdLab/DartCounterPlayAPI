package dartServer.gameengine;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.listeners.*;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.Player;
import dartServer.networking.Client;
import dartServer.networking.NetworkManager;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Control class for the server-side game logic and infrastructure. Games take place in lobbies.
 * players with their SocketAdress are managed via mapping.
 */
public class GameEngine {

    private static final HashMap<SocketAddress, Player> players = new HashMap<>();
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
        players.clear();
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

    public static Player createPlayer(String name, Client client) {
        Player player = new Player(name, client);
        if (addPlayer(player)) {
            return player;
        }
        return null;
    }

    public static Lobby createLobby(Player player) {
        if (player.getLobbyId() == null) {
            Lobby lobby = new Lobby(player);
            lobbies.add(new Lobby(player));
            return lobby;
        }
        return null;
    }

    public static Lobby joinLobby(Player player, int code) {
        if (player.getLobbyId() == null) {
            Lobby lobby = getLobbyByCode(code);
            if (lobby != null) {
                if (lobby.addPlayer(player)) {
                    return lobby;
                }
            }
        }
        return null;
    }


    public static Player getPlayer(SocketAddress address) {
        return players.get(address);
    }

    public static Lobby getLobbyByPlayer(Player player) {
        Integer lobbyId = player.getLobbyId();
        if (lobbyId == null) return null;

        for (Lobby lobby : lobbies) {
            if (lobby.getId() == lobbyId)
                return lobby;
        }
        return null;
    }

    public static Player[] getPlayers() {
        return players.values().toArray(new Player[0]);
    }


    private static boolean addPlayer(Player player) {
        SocketAddress address = player.getClient().getAddress();
        if (!players.containsKey(address)) {
            players.put(address, player);
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
        Arrays.stream(lobby.getPlayers()).forEach(player -> player.sendMessage(packet));
    }

    public static void updatePlayerAddress(SocketAddress address, Player player) {
        players.remove(address);
        addPlayer(player);
    }

    public static void removePlayer(Player player) {
        SocketAddress address = player.getClient().getAddress();
        players.remove(address);
        if (player.getLobbyId() != null) {
            getLobbyByPlayer(player).getGame().removePlayer(player);
        }
    }

    public static Player getPlayerByName(String name) {
        return Arrays.stream(getPlayers()).filter(player -> player.getName().equals(name)).findFirst().orElse(null);
    }

    public static void removeLobby(Lobby lobby) {
        lobbies.remove(lobby);
    }

    public static List<Lobby> getLobbies() {
        return lobbies;
    }
}
