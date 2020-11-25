package dartServer.gameengine.lobby;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.Game;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.model.Throw;

import java.util.Arrays;

public class Lobby {

    private static int lobbyCount = 1;

    private final int id;

    private final Game game;


    // --== Constructor ==--

    public Lobby(Player player) {
        game = new Game(player);
        id = lobbyCount++;
    }


    // --== Methods ==--

    public boolean addPlayer(Player player) {
        return game.addPlayer(player);
    }

    public void removePlayer(Player player) {
        game.removePlayer(player);
    }

    public boolean startGame(Player player) {
        return game.start(player);
    }

    public boolean performThrow(Player player, Throw t) {
        return game.performThrow(player, t);
    }

    public boolean undoThrow(Player player) {
        return game.undoThrow(player);
    }

    /**
     * broadcasts the given packet to all Clients in the Lobby.
     *
     * @param packet the packet that needs to be sent
     */
    public void broadcastToPlayers(ResponsePacket packet) {
        for (Player player : game.getPlayers()) {
            if (player.isConnected())
                player.sendMessage(packet);
        }
    }


    // --== Getter/Setter ==--

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Player[] getPlayers() {
        return Arrays.stream(GameEngine.getPlayers()).filter(player -> player.getLobbyId() == id).toArray(Player[]::new);
    }

    public int getCode() {
        return id + 999;
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "id=" + id + ", " +
                "code=" + getCode() +
                '}';
    }

}
