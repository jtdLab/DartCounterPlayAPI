package dartServer.gameengine.lobby;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.model.Player;
import dartServer.networking.Client;

public class User {

    private final String uid;
    private final String username;
    private Client client;

    private Integer lobbyId; // null if part of no lobby
    private Player player; // null if not playing

    public User(String uid, String username, Client client) {
        this.uid = uid;
        this.username = username;
        this.client = client;
    }

    // --== Methods ==--

    public void sendMessage(ResponsePacket packet) {
        client.sendPacket(packet);
    }

    public boolean isConnected() {
        return client != null;
    }

    public boolean isPlaying() {
        return player != null;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Integer lobbyId) {
        this.lobbyId = lobbyId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", lobbyId='" + lobbyId + '\'' +
                '}';
    }
}
