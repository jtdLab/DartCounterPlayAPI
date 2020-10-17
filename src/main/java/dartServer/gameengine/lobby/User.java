package dartServer.gameengine.lobby;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.networking.Client;

public class User {

    private String name;
    private String lobbyName;
    private String password;
    private boolean isPlayer;

    private Client client; // client for networking

    // --== Constructors ==--

    /**
     * User that did not send loginRequest yet
     *
     * @param client The client of the user
     */
    public User(Client client) {
        this.client = client;
    }

    /**
     * instantiates a user with unknown or empty mods
     * user is spectator, as long as team is null
     *
     * @param client                   client for networking
     * @param name                     name of the user
     * @param lobbyName                The name of the lobby
     * @param password                 password of the user
     */
    public User(Client client, String name, String lobbyName, String password) {
        this.client = client;
        this.name = name;
        this.lobbyName = lobbyName;
        this.password = password;
        this.isPlayer = false;
    }

    // --== Methods ==--

    public void sendMessage(ResponsePacket packet) {
        client.sendPacket(packet);
    }

    // --== Getter/Setter ==--

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public boolean isConnected() {
        return client != null;
    }

}
